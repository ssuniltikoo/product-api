package com.project.products.productapi.service.impl;

import com.project.products.productapi.config.ApiClient;
import com.project.products.productapi.dtos.FakeStoreProductDto;
import com.project.products.productapi.exceptions.InvalidProductIdException;
import com.project.products.productapi.exceptions.ProductNotFoundException;
import com.project.products.productapi.exceptions.ServiceNotFoundException;
import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        String url = URL + "/{productId}" ;
        if(productId == null || productId <= 0){
            throw new InvalidProductIdException("Product id must be a positive number and " +
                    "greater than zero. Provided id: " + productId);
        }
       ResponseEntity<FakeStoreProductDto> fakeStoreProductResponse =  apiClient.getRestTemplate().
                getForEntity(url , FakeStoreProductDto.class, productId);

        if(fakeStoreProductResponse.getStatusCode().is2xxSuccessful() && fakeStoreProductResponse.hasBody()){
            FakeStoreProductDto fakeStoreProductDto = fakeStoreProductResponse.getBody();
            if(fakeStoreProductDto == null) {
                throw new ProductNotFoundException("Product not found for id : " + productId);
            }
            return fakeStoreProductDto.toProduct();
        }

        if (fakeStoreProductResponse.getStatusCode().is4xxClientError()) {
            throw new ProductNotFoundException("client error Invalid product id : " + productId);
        }
        if (fakeStoreProductResponse.getStatusCode().is5xxServerError()) {
            throw new ServiceNotFoundException("Internal Server error");
        }

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
