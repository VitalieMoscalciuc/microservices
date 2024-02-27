package com.vmoscalciuc.productservice.model.productType;

import com.vmoscalciuc.productservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Notebook extends Product {

    private String processor;
    private String videoCard;
    private Integer ramSize;
    private String storageType;
    private Integer storageSize;
    private Double displaySize;

}