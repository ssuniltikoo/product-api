package com.project.products.productapi.controllers;

import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") long productId) {
        Product product = productService.getSingleProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
