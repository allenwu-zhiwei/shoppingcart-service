package com.nusiss.shoppingcart_service.service.impl;

import com.nusiss.shoppingcart_service.entity.Cart;
import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.exception.CartNotFoundException;
import com.nusiss.shoppingcart_service.exception.InsufficientStockException;
import com.nusiss.shoppingcart_service.exception.ProductNotFoundException;
import com.nusiss.shoppingcart_service.repository.CartItemRepository;
import com.nusiss.shoppingcart_service.repository.CartRepository;
import com.nusiss.shoppingcart_service.service.InventoryServiceClient;
import com.nusiss.shoppingcart_service.service.ProductServiceClient;
import com.nusiss.shoppingcart_service.service.ShoppingCartService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductServiceClient productServiceClient;

    private final InventoryServiceClient inventoryServiceClient;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductServiceClient productServiceClient, InventoryServiceClient inventoryServiceClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productServiceClient = productServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @Override
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long cartId, CartItem cartItem) {
        // check product exist and stock
        /*boolean productExists = productServiceClient.isProductAvailable(cartItem.getProductId());
        if (!productExists) {
            throw new ProductNotFoundException("Product not found with id: " + cartItem.getProductId());
        }

        boolean stockAvailable = inventoryServiceClient.isStockAvailable(cartItem.getProductId(), cartItem.getQuantity());
        if (!stockAvailable) {
            throw new InsufficientStockException("Not enough stock for product id: " + cartItem.getProductId());
        }*/

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        cartItemRepository.save(cartItem);
        return cart;
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartNotFoundException("Cart item not found with id: " + cartItemId));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new CartNotFoundException("Cart item does not belong to the cart with id: " + cartId);
        }

        boolean stockAvailable = inventoryServiceClient.isStockAvailable(cartItem.getProductId(), quantity);
        if (!stockAvailable) {
            throw new InsufficientStockException("Not enough stock for product id: " + cartItem.getProductId());
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cart;
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartNotFoundException("CartItem not found with id: " + cartItemId));

        cartItemRepository.delete(cartItem);
        return cart;
    }

    @Override
    @Transactional
    public Cart clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        List<CartItem> cartItems = cart.getCartItems();
        cartItemRepository.deleteAll(cartItems);
        return cart;
    }

    @Override
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        return cart.getCartItems();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user with ID: " + userId));
    }

}
