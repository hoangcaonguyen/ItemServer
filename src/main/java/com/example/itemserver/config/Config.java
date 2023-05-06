package com.example.itemserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory("http://localhost:8082");
//        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
//        return restTemplate;
//    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
