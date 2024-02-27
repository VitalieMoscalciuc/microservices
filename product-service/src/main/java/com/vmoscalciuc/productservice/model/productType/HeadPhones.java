package com.vmoscalciuc.productservice.model.productType;

import com.vmoscalciuc.productservice.model.Product;
import lombok.AllArgsConstructor;
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
public class HeadPhones extends Product {

    private String connectionType;
    private Boolean noiseCancellation;
    private String frequencyInterval;

}
