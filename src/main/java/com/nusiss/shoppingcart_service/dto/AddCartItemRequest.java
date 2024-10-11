package com.nusiss.shoppingcart_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddCartItemRequest {

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 1, message = "Product ID must be greater than 0")
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "Cart ID cannot be null")
    @Min(value = 1, message = "Cart ID must be greater than 0")
    @NotNull(message = "Cart ID cannot be null")
    private Long cartId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // Add getter methods
    public Long getProductId() {
        return productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
