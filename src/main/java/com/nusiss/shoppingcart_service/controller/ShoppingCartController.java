package com.nusiss.shoppingcart_service.controller;

import com.nusiss.shoppingcart_service.config.UserPrincipal;
import com.nusiss.shoppingcart_service.dto.AddCartItemRequest;
import com.nusiss.shoppingcart_service.dto.UpdateCartItemQuantityRequest;
import com.nusiss.shoppingcart_service.entity.Cart;
import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.service.ShoppingCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //之后通过redis获取userid
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestParam Long userId) {
        Cart cart = shoppingCartService.createCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


    @PostMapping("/add-item")
    public ResponseEntity<Cart> addItemToCart(@RequestParam Long userId, @Valid @RequestBody AddCartItemRequest request) {
        Cart cart = shoppingCartService.getCartByUserId(userId);

        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CartItem cartItem = new CartItem();
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCart(cart);
        cartItem.setCreateUser("default_user");
        cart = shoppingCartService.addItemToCart(request.getCartId(), cartItem);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long userId) {
        Cart cart = shoppingCartService.getCartByUserId(userId);
        List<CartItem> items = shoppingCartService.getCartItems(cart.getCartId());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/update-item-quantity")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestParam Long userId, @Valid @RequestBody UpdateCartItemQuantityRequest request) {
        Cart cart = shoppingCartService.updateItemQuantity(request.getCartId(), request.getCartItemId(), request.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@RequestParam Long userId, @PathVariable Long cartItemId) {
        Cart cart = shoppingCartService.getCartByUserId(userId);
        cart = shoppingCartService.removeItemFromCart(cart.getCartId(), cartItemId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestParam Long userId) {
        Cart cart = shoppingCartService.getCartByUserId(userId);
        cart = shoppingCartService.clearCart(cart.getCartId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
