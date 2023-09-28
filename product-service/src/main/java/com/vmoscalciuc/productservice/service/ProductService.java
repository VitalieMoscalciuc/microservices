package com.vmoscalciuc.productservice.service;

import com.vmoscalciuc.productservice.dto.*;
import com.vmoscalciuc.productservice.model.*;
import com.vmoscalciuc.productservice.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }


    public List<ProductResponse> getAllProducts() {
        log.info("getting all products");
        List <Product> productsList =  productRepository.findAll();
        return productsList.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
