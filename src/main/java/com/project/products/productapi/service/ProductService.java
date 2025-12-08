package com.project.products.productapi.service;

import com.project.products.productapi.model.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId);
    List<Product> getAllProducts();
    //List<Product> getProductsByCategory(String category);
    Product createProduct(Product product);
    Product updateProduct(Product product, Long id);
}
