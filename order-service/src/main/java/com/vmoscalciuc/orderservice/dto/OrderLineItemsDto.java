package com.vmoscalciuc.orderservice.dto;

import lombok.*;

import java.math.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
