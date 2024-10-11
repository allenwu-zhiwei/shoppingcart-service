package com.nusiss.shoppingcart_service.config;

public class UserPrincipal {
    private Long userId;
    private String username;

    public UserPrincipal(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
