package com.vmoscalciuc.event;

import com.vmoscalciuc.dto.OrderLineItemsDto;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
    private List<OrderLineItemsDto> products;
}
