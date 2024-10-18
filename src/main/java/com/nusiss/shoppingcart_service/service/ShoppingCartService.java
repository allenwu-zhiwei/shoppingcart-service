package com.nusiss.shoppingcart_service.service;

import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;
import com.nusiss.shoppingcart_service.entity.Cart;
import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.exception.CartNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;

import java.util.List;

public interface ShoppingCartService {

    Cart createCart(Integer userId);

    Cart addItemToCart(Long cartId, CartItem cartItem);

    Cart updateItemQuantity(Long cartId, Long cartItemId, int quantity);

    Cart removeItemFromCart(Long cartId, Long cartItemId);

    Cart clearCart(Long cartId);

    List<CartItem> getCartItems(Long cartId);

    Cart getCartByUserId(Integer userId);

    public ResponseEntity<ApiResponse<User>> getUserById(Integer id);

    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(String authToken);

}
