package com.nusiss.shoppingcart_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateCartItemQuantityRequest {
    @NotNull(message = "Cart ID cannot be null")
    @Min(value = 1, message = "Cart ID must be greater than 0")
    private Long cartId;
    @NotNull(message = "Cart Item ID cannot be null")
    @Min(value = 1, message = "Cart Item ID must be greater than 0")
    @NotNull(message = "Cart item ID cannot be null")
    private Long cartItemId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // Add getter methods

    public Long getCartId() {
        return cartId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}