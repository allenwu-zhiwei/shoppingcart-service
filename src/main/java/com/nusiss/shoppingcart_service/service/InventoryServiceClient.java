package com.nusiss.shoppingcart_service.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {
    @GetMapping("/inventory/check")
    boolean check(@RequestParam("productId") Long productId, @RequestParam("num") int num);
}


