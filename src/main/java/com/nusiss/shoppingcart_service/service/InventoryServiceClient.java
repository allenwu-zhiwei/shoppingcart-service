package com.nusiss.shoppingcart_service.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {
    @GetMapping("/api/inventory/{productId}/available")
    boolean isStockAvailable(@PathVariable Long productId, @RequestParam int quantity);
}


