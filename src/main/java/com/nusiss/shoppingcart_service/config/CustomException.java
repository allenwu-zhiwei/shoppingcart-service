package com.nusiss.shoppingcart_service.config;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}