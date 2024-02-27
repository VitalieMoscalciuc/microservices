package com.vmoscalciuc.orderservice.event;

import com.vmoscalciuc.orderservice.dto.OrderLineItemsDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
    private List<OrderLineItemsDto> products;
}
