package com.vmoscalciuc.orderservice.config;

import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.client.*;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
