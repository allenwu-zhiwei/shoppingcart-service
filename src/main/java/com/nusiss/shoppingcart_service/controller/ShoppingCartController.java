package com.nusiss.shoppingcart_service.controller;

import com.nusiss.shoppingcart_service.config.UserPrincipal;
import com.nusiss.shoppingcart_service.dto.AddCartItemRequest;
import com.nusiss.shoppingcart_service.dto.UpdateCartItemQuantityRequest;
import com.nusiss.shoppingcart_service.entity.Cart;
import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.service.ShoppingCartService;
import com.nusiss.shoppingcart_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;
import com.nusiss.commonservice.feign.UserFeignClient;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = response.getBody().getData();
        Cart cart = shoppingCartService.createCart(user.getUserId());
        cart.setCreateUser(user.getUsername());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/add-item")
    public ResponseEntity<Cart> addItemToCart(@RequestHeader("authToken") String authToken, @Valid @RequestBody AddCartItemRequest request) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = response.getBody().getData();
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());

        if (cart == null) {
            cart = shoppingCartService.createCart(user.getUserId());
        }

        CartItem cartItem = new CartItem();
        cartItem.setProductId(request.getProductId());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCart(cart);
        cartItem.setCreateUser(user.getUsername());
        cart = shoppingCartService.addItemToCart(request.getCartId(), cartItem);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = response.getBody().getData();
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        if (cart == null) {
            cart = shoppingCartService.createCart(user.getUserId());
        }
        List<CartItem> items = shoppingCartService.getCartItems(cart.getCartId());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/update-item-quantity")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestHeader("authToken") String authToken, @Valid @RequestBody UpdateCartItemQuantityRequest request) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Cart cart = shoppingCartService.updateItemQuantity(request.getCartId(), request.getCartItemId(), request.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@RequestHeader("authToken") String authToken, @PathVariable Long cartItemId) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = response.getBody().getData();
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        cart = shoppingCartService.removeItemFromCart(cart.getCartId(), cartItemId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = response.getBody().getData();
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        cart = shoppingCartService.clearCart(cart.getCartId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
