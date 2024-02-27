package com.vmoscalciuc.productservice.controller;

import com.vmoscalciuc.productservice.dto.*;
import com.vmoscalciuc.productservice.service.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createMouse")
    public ResponseEntity<String> createMouse(@RequestBody MouseRequest productRequest) {
        productService.createMouse(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mouse created successfully");
    }

    @PostMapping("/createHeadphones")
    public ResponseEntity<String> createHeadphones(@RequestBody HeadPhoneRequest productRequest) {
        productService.createHeadphones(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Headphones created successfully");
    }

    @PostMapping("/createNotebook")
    public ResponseEntity<String> createNotebook(@RequestBody NotebookRequest productRequest) {
        productService.createNotebook(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notebook created successfully");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/allByIds")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductPriceRequest> getProductListByOrderIds(@RequestParam List<String> productId){
        return productService.getAllProductsById(productId);
    }
}