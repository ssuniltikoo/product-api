package com.project.products.productapi.service.impl;

import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;

import java.util.List;

public class FakeStoreApiService implements ProductService {
    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        return null;
    }
}
