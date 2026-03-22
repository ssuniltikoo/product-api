package com.project.products.productapi.controllers;

import com.project.products.productapi.dtos.CreateProductRequest;
import com.project.products.productapi.dtos.FakeStoreProductDto;
import com.project.products.productapi.dtos.ProductDto;
import com.project.products.productapi.exceptions.ProductNotFoundException;
import com.project.products.productapi.model.Category;
import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// version 1 of the api /v1/products.
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    /*
        * This method is responsible for retrieving the details of a single product based on the provided product ID.
     */
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
    /*
        * This method is responsible for creating a new product based on the provided CreateProductRequest.
     */
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

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id ){
       boolean result =productService.deleteProduct(id);
       if(result){
           log.info("Product with id: {} deleted successfully", id);
       } else {
           log.info("Failed to delete product with id: {}", id);
           throw new ProductNotFoundException("Product not found for id : " + id);
       }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long productId,
                                                     @RequestBody ProductDto productDto) {
        log.info("Replace product request received for product with id: {}", productId);
        Product product = from(productDto);
        Product response = productService.replaceProduct(productId, product);
        if(response != null) {
            ProductDto responseDto = ProductDto.from(response);
            log.info("Product with id: {} replaced successfully", productId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        log.info("Failed to replace product with id: {}", productId);
        throw new ProductNotFoundException("Product not found for id : " + productId);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        log.info("Get all products request received");
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product>products = productService.getAllProducts();
        if (products != null) {
            for(Product product : products) {
                ProductDto productDto = ProductDto.from(product);
                productDtos.add(productDto);
            }
            return productDtos;
        }

        return null;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setId(productDto.getCategory().getId());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
