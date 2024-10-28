package com.nusiss.shoppingcart.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory")
public interface InventoryServiceClient {
    @GetMapping("/check/{productId}/{num}")
    boolean check(@PathVariable("productId") Long productId, @PathVariable("num") int num);
}


