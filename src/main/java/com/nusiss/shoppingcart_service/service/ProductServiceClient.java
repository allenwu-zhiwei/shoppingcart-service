package com.nusiss.shoppingcart_service.service;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/products/{productId}/available")
    boolean isProductAvailable(@PathVariable Long productId);
}


