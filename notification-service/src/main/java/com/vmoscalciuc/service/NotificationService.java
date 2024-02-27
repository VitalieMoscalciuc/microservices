package com.vmoscalciuc.service;

import com.vmoscalciuc.dto.OrderLineItemsDto;
import com.vmoscalciuc.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final WebClient.Builder webClientBuilder;

    @KafkaListener(topics = "notificationTopic", groupId = "notificationId")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());

        List<OrderLineItemsDto> products = orderPlacedEvent.getProducts();
            for (OrderLineItemsDto productInfo : products) {
                log.info("Product ID: {}, Quantity: {}", productInfo.getProductId(), productInfo.getQuantity());
            }
        }

}
