package com.project.products.productapi.service.impl;

import com.project.products.productapi.config.ApiClient;
import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FakeStoreApiService implements ProductService {

    private final ApiClient apiClient;

    private final static  String URL = "https://fakestoreapi.com/products";

    @Autowired
    public FakeStoreApiService(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        return apiClient.getRestTemplate().
                getForObject(URL + "/" + productId, Product.class);
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
