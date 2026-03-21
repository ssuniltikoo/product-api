package com.project.products.productapi.controllers;

import com.project.products.productapi.dtos.ProductDto;
import com.project.products.productapi.exceptions.ProductNotFoundException;
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
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("id") long productId) {
        Product product = productService.getSingleProduct(productId);
        if(product==null){
            log.info("Product not found for id : " + productId);
            throw new ProductNotFoundException("Product not found for id : " + productId);
        }
        ProductDto productDto = ProductDto.from(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }





}
