package com.project.products.productapi.controllers;

import com.project.products.productapi.dtos.CreateProductRequest;
import com.project.products.productapi.dtos.FakeStoreProductDto;
import com.project.products.productapi.dtos.ProductDto;
import com.project.products.productapi.exceptions.ProductNotFoundException;
import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("id") long productId) {
        log.info("Get product request received for product with id: {}", productId);
        Product product = productService.getSingleProduct(productId);
        if(product==null){
            log.info("Product not found for id : {}", productId);
            throw new ProductNotFoundException("Product not found for id : " + productId);
        }
        ProductDto productDto = ProductDto.from(product);
        log.info("Product details retrieved successfully for product with id: {}", productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest productRequest) {
        log.info("Create product request received for product with title: {}", productRequest.getTitle());
        if(productRequest.getTitle() == null || productRequest.getTitle().isEmpty()) {
            log.info("Invalid product title provided: {}", productRequest.getTitle());
            throw new IllegalArgumentException("Product title cannot be null or empty");
        }
        Product product = productService.createProduct(productRequest.getTitle(), productRequest.getDescription(),
                productRequest.getCategory(), productRequest.getPrice(), productRequest.getImage());
        if(product==null){
            log.info("Product not found for id : " );
            throw new ProductNotFoundException("Product not found");
        }
        ProductDto productDto = ProductDto.from(product);
        log.info("Product created successfully with id: {}", product.getId());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
