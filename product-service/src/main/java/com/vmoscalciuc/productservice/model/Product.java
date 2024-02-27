package com.vmoscalciuc.productservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.math.*;

@Document(value = "product")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String brand;
    private BigDecimal price;
    @Field(targetType = FieldType.BINARY)
    private byte[] photo;
}
