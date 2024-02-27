package com.vmoscalciuc.productservice.service;

import com.vmoscalciuc.productservice.dto.*;
import com.vmoscalciuc.productservice.exception.ProductNotFoundException;
import com.vmoscalciuc.productservice.model.*;
import com.vmoscalciuc.productservice.model.productType.HeadPhones;
import com.vmoscalciuc.productservice.model.productType.Mouse;
import com.vmoscalciuc.productservice.model.productType.Notebook;
import com.vmoscalciuc.productservice.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createMouse(MouseRequest productRequest) {
        Mouse mouse = Mouse.builder()
                .name(productRequest.getName())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .dpi(productRequest.getDpi())
                .build();
        productRepository.save(mouse);
        log.info("Mouse {} is saved", mouse.getId());
    }

    public void createHeadphones(HeadPhoneRequest productRequest) {
        HeadPhones headphones = HeadPhones.builder()
                .name(productRequest.getName())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .connectionType(productRequest.getConnectionType())
                .noiseCancellation(productRequest.getNoiseCancellation())
                .frequencyInterval(productRequest.getFrequencyInterval())
                .build();
        productRepository.save(headphones);
        log.info("Headphones {} are saved", headphones.getId());
    }

    public void createNotebook(NotebookRequest productRequest) {
        Notebook notebook = Notebook.builder()
                .name(productRequest.getName())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .processor(productRequest.getProcessor())
                .videoCard(productRequest.getVideoCard())
                .ramSize(productRequest.getRamSize())
                .storageSize(productRequest.getStorageSize())
                .storageType(productRequest.getStorageType())
                .displaySize(productRequest.getDisplaySize())
                .build();
        productRepository.save(notebook);
        log.info("Headphones {} are saved", notebook.getId());
    }


    public List<ProductResponse> getAllProducts() {
        log.info("Getting all products");
        List<Product> productsList = productRepository.findAll();
        return productsList.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(String id) {
        log.info("Getting product with id={} ", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Provided product is not found"));
        return mapToProductResponse(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .build();
    }

    private ProductPriceRequest mapToProduct(Product product) {
        return ProductPriceRequest.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProductPriceRequest> getAllProductsById(List<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        return products.stream()
                .map(this::mapToProduct)
                .toList();
    }

}
