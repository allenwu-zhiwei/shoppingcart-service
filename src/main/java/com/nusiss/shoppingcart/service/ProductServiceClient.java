package com.nusiss.shoppingcart.service;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.shoppingcart.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/product")
    ResponseEntity<ApiResponse<ProductDTO>> productInfo(@RequestParam("productId") Long productId);
}


