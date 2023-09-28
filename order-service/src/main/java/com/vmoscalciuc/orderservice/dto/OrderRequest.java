package com.vmoscalciuc.orderservice.dto;

import com.vmoscalciuc.orderservice.model.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
