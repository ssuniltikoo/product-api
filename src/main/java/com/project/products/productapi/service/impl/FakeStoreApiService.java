package com.project.products.productapi.service.impl;

import com.project.products.productapi.config.ApiClient;
import com.project.products.productapi.dtos.FakeStoreProductDto;
import com.project.products.productapi.exceptions.InvalidProductIdException;
import com.project.products.productapi.exceptions.ProductNotFoundException;
import com.project.products.productapi.exceptions.ServiceNotFoundException;
import com.project.products.productapi.model.Product;
import com.project.products.productapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FakeStoreApiService implements ProductService {

    private final ApiClient apiClient;

    private final static  String URL = "https://fakestoreapi.com/products";
    private final RestTemplate getRestTemplate;

    @Autowired
    public FakeStoreApiService(final ApiClient apiClient, RestTemplate getRestTemplate) {
        this.apiClient = apiClient;
        this.getRestTemplate = getRestTemplate;
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
            log.info("Product details retrieved successfully for product with id: {}", productId);
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
        List<Product> products = new ArrayList<>();
        ResponseEntity<FakeStoreProductDto[]> fakeStoreListResponse  =
        apiClient.getRestTemplate().getForEntity(URL, FakeStoreProductDto[].class);
        if (fakeStoreListResponse.hasBody() &&
                fakeStoreListResponse.getStatusCode().is2xxSuccessful()) {
            FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreListResponse.getBody();
            for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
                products.add(fakeStoreProductDto.toProduct());
            }
            log.info("Product list retrieved successfully with total products: {}", products.size());
            return products;
        }
        return products;
    }

    @Override
    public Product createProduct(String title, String description, String category, double price, String image){
        FakeStoreProductDto product = new FakeStoreProductDto();
        product.setTitle(title);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setImage(image);

      ResponseEntity<FakeStoreProductDto> fakeStoreProductResponse = apiClient.getRestTemplate().postForEntity(URL,
              product, FakeStoreProductDto.class);

        if(fakeStoreProductResponse.getStatusCode().is2xxSuccessful() && fakeStoreProductResponse.hasBody()){
            FakeStoreProductDto fakeStoreProductDto = fakeStoreProductResponse.getBody();
            if(fakeStoreProductDto == null) {
                throw new ProductNotFoundException("Product not found for id : " + product.getId()  );
            }
            log.info("Product created successfully with id: {}", product.getId());
            return fakeStoreProductDto.toProduct();
        }

        if (fakeStoreProductResponse.getStatusCode().is4xxClientError()) {
            throw new ProductNotFoundException("client error Invalid product id : " + product.getId());
        }
        if (fakeStoreProductResponse.getStatusCode().is5xxServerError()) {
            throw new ServiceNotFoundException("Internal Server error");
        }
        return null;
    }



    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fake = new FakeStoreProductDto();
        fake.setTitle(product.getName());
        fake.setDescription(product.getDescription());
        fake.setPrice(product.getPrice());
        fake.setImage(product.getImageUrl());
        fake.setId(product.getId());
        fake.setCategory(product.getCategory().getName());
//        ResponseEntity<FakeStoreProductDto> responseEntity   = apiClient.getRestTemplate().postForEntity
//        (URL + "/{id}", HttpMethod.PUT, FakeStoreProductDto.class, id);
         RequestCallback requestCallback = apiClient.getRestTemplate().httpEntityCallback(URL + "/{id}",
                    FakeStoreProductDto.class);
         ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = getRestTemplate
                 .responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> responseEntity = getRestTemplate.execute(URL + "/{id}", HttpMethod.PUT,
                 requestCallback, responseExtractor, id);

        if(responseEntity.hasBody() && responseEntity.getStatusCode().is2xxSuccessful()){
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
            if(fakeStoreProductDto == null) {
                throw new ProductNotFoundException("Product not found for id : " + id);
            }
            log.info("Product updated successfully with id: {}", id);
            return fakeStoreProductDto.toProduct();
        }

        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

}
