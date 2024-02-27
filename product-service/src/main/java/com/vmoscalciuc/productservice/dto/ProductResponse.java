package com.vmoscalciuc.productservice.dto;

import lombok.*;
import org.springframework.data.annotation.*;

import java.math.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {
    @Id
    private String id;
    private String name;
    private String brand;
    private BigDecimal price;
}
