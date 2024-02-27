package com.vmoscalciuc.inventoryservice.service;

import com.vmoscalciuc.inventoryservice.dto.*;
import com.vmoscalciuc.inventoryservice.event.OrderPlacedEvent;
import com.vmoscalciuc.inventoryservice.exception.ProductNotFoundException;
import com.vmoscalciuc.inventoryservice.exception.ProductNotFoundInInventoryException;
import com.vmoscalciuc.inventoryservice.model.Inventory;
import com.vmoscalciuc.inventoryservice.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isProductStock(List<OrderLineItemsDto> products) {
        return products.stream()
                .map(this::checkProductStock)
                .collect(Collectors.toList());
    }

    private InventoryResponse checkProductStock(OrderLineItemsDto product) {
        Inventory inventory = inventoryRepository.findByProductId(product.getProductId())
                .orElseThrow(() -> new ProductNotFoundInInventoryException("Product not found in inventory: " + product.getProductId()));

        boolean isInStock = inventory.getQuantity() >= product.getQuantity();
        return InventoryResponse.builder()
                .productId(product.getProductId())
                .isInStock(isInStock)
                .build();
    }


//    @Transactional(readOnly = true)
//    public List<InventoryResponse> IsInStock(List<String> productIds) {
//        return productIds.stream()
//                .map(productId -> inventoryRepository.findByProductId(productId)
//                        .orElseThrow(
//                                ()-> new ProductNotFoundInInventoryException("This product dont exist in inventory"))
//                ).map(inventory -> {
//                    boolean isInStock = inventory.getQuantity() > 0;
//                    return InventoryResponse.builder()
//                            .productId(inventory.getProductId())
//                            .isInStock(isInStock)
//                            .build();
//                })
//                .toList();
//    }

    @Transactional
    public void updateQuantity(OrderLineItemsDto productInfo){
        inventoryRepository.updateQuantityOfProducts(productInfo.getProductId(),productInfo.getQuantity());
    }

    @KafkaListener(topics = "notificationTopic", groupId = "notificationId2")
    @Transactional
    public void modifyInventoryQuantity(OrderPlacedEvent orderPlacedEvent){
        log.info("Receiver message");
        List<OrderLineItemsDto> products = orderPlacedEvent.getProducts();
        if (products != null) {
            for (OrderLineItemsDto productInfo : products) {
                inventoryRepository.updateQuantityOfProducts(productInfo.getProductId(),productInfo.getQuantity());
            }
        }
    }
}
