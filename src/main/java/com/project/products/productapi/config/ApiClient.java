package com.project.products.productapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiClient {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
