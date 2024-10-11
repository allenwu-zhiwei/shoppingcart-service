package com.nusiss.shoppingcart_service.service;

import com.nusiss.shoppingcart_service.entity.Cart;
import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.exception.CartNotFoundException;

import java.util.List;

public interface ShoppingCartService {

    Cart createCart(Long userId);

    Cart addItemToCart(Long cartId, CartItem cartItem);

    Cart updateItemQuantity(Long cartId, Long cartItemId, int quantity);

    Cart removeItemFromCart(Long cartId, Long cartItemId);

    Cart clearCart(Long cartId);

    List<CartItem> getCartItems(Long cartId);

    Cart getCartByUserId(Long userId);

}
