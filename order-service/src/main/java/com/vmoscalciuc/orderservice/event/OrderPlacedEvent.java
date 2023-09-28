package com.vmoscalciuc.orderservice.event;

import lombok.*;
import lombok.extern.slf4j.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
