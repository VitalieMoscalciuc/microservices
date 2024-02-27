package com.vmoscalciuc.orderservice.service;

import com.vmoscalciuc.orderservice.dto.*;
import com.vmoscalciuc.orderservice.enums.Status;
import com.vmoscalciuc.orderservice.event.*;
import com.vmoscalciuc.orderservice.model.*;
import com.vmoscalciuc.orderservice.repository.*;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .quantity(orderLineItemsDto.getQuantity())
                .productId(orderLineItemsDto.getProductId())
                .build();
    }

    public String placeOrder(OrderRequest orderRequest) {
        Order order = createOrder(orderRequest);
        List<String> productIds = extractProductIds(order);
        Map<String, BigDecimal> productIdToPriceMap = getProductPrices(productIds);
        List<OrderLineItemsDto> orderLineItemsDtoList =  orderRequest.getOrderLineItemsDtoList();
        BigDecimal totalPrice = calculateTotalPrice(order, productIdToPriceMap);
        order.setTotalPrice(totalPrice);
        if(checkProductAvailability(orderLineItemsDtoList)){
            saveOrderAndNotify(order);
            return "Order placed successfully, total price is " + totalPrice;
        }else{
            throw new IllegalArgumentException("One or more products are not in stock, please try again later");
        }
    }

    private Order createOrder(OrderRequest orderRequest) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(mapToOrderLineItems(orderRequest.getOrderLineItemsDtoList()))
                .userEmail(orderRequest.getUserEmail())
                .userAddress(orderRequest.getUserAddress())
                .status(Status.PENDING)
                .build();
    }

    private List<OrderLineItems> mapToOrderLineItems(List<OrderLineItemsDto> orderLineItemsDtoList) {
        return orderLineItemsDtoList.stream()
                .map(this::mapToDto)
                .toList();
    }

    private List<String> extractProductIds(Order order) {
        return order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getProductId)
                .toList();
    }

    private Map<String, BigDecimal> getProductPrices(List<String> productIds) {
        ProductResponse[] productResponse = getProductResponse(productIds);
        return Arrays.stream(productResponse)
                .collect(Collectors.toMap(ProductResponse::getProductId, ProductResponse::getPrice));
    }

    private BigDecimal calculateTotalPrice(Order order, Map<String, BigDecimal> productIdToPriceMap) {
        return order.getOrderLineItemsList().stream()
                .map(orderLineItem -> {
                    BigDecimal price = productIdToPriceMap.get(orderLineItem.getProductId());
                    return price.multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProductResponse[] getProductResponse(List<String> productIds) {
        return webClientBuilder.build().get()
                .uri("http://product-service/api/product/allByIds", uriBuilder -> uriBuilder
                        .queryParam("productId", productIds).build())
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();

    }

    public boolean checkProductAvailability(List<OrderLineItemsDto> productInfos) {
        String uri = "http://inventory-service/api/inventory";
        Mono<InventoryResponse[]> inventoryResponseMono = webClientBuilder.build()
                .post()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(productInfos))
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .onErrorReturn(new InventoryResponse[0]); // Return empty array on error

        InventoryResponse[] inventoryResponseArray = inventoryResponseMono.block();
        return inventoryResponseArray != null &&
                Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
    }

    private void saveOrderAndNotify(Order order) {
        orderRepository.save(order);
        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setOrderNumber(order.getOrderNumber());
        List<OrderLineItemsDto> productInfos = new ArrayList<>();
        for (OrderLineItems lineItem : order.getOrderLineItemsList()) {
            productInfos.add(new OrderLineItemsDto(lineItem.getProductId(), lineItem.getQuantity()));
        }
        event.setProducts(productInfos);
        kafkaTemplate.send("notificationTopic", event);
    }

    @KafkaListener(topics = "order-delivered-topic", groupId = "order-service-group")
    public void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(()->new IllegalArgumentException("No such order in system"));
        order.setStatus(Status.DELIVERED);
        orderRepository.save(order);
    }
}
