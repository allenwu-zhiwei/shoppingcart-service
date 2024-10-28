package com.nusiss.shoppingcart.service;

import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;
import com.nusiss.shoppingcart.dto.CartInfoDTO;
import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShoppingCartService {

    Cart createCart(Integer userId, String createUser, String updateUser);

    boolean addItemToCart(Cart cart, Long productId, int quantity, double price, String createUser);

    boolean updateItemQuantity(Cart cart, Long cartItemId, int quantity);

    boolean removeItemFromCart(Long cartId, Long cartItemId);

    boolean clearCart(Long cartId);

    List<CartItem> getCartItems(Long cartId);

    Cart getCartByUserId(Integer userId);

    public ResponseEntity<ApiResponse<User>> getUserById(Integer id);

    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(String authToken);

    public CartInfoDTO convertToDTO(CartItem cartItem);

}
