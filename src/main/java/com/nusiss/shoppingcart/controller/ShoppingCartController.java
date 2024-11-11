package com.nusiss.shoppingcart.controller;

import com.nusiss.shoppingcart.dto.AddCartItemRequest;
import com.nusiss.shoppingcart.dto.CartInfoDTO;
import com.nusiss.shoppingcart.dto.UpdateCartItemQuantityRequest;
import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.service.CartItemService;
import com.nusiss.shoppingcart.service.ShoppingCartService;
import com.nusiss.shoppingcart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired//NOSONAR
    private ShoppingCartService shoppingCartService;

    @Autowired//NOSONAR
    private CartItemService cartItemService;

    @Qualifier("userServiceImpl")
    @Autowired//NOSONAR
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }//NOSONAR

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.createCart(user.getUserId(), user.getUsername(), user.getUsername());

        cart.setCreateDatetime(LocalDateTime.now());
        cart.setUpdateDatetime(LocalDateTime.now());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestHeader("authToken") String authToken, @Valid @RequestBody AddCartItemRequest request) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        if (cart == null) {
            cart = shoppingCartService.createCart(user.getUserId(), user.getUsername(), user.getUsername());
        }
        boolean success = shoppingCartService.addItemToCart(cart, request.getProductId(), request.getQuantity(), request.getPrice(), user.getUsername());
        if (success) {
            return new ResponseEntity<>("Item successfully added to cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add item to cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        if (cart == null) {
            cart = shoppingCartService.createCart(user.getUserId(), user.getUsername(), user.getUsername());
        }
        List<CartItem> items = shoppingCartService.getCartItems(cart.getCartId());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/update-item-quantity")
    public ResponseEntity<String> updateCartItemQuantity(@RequestHeader("authToken") String authToken, @Valid @RequestBody UpdateCartItemQuantityRequest request) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());

        boolean success = shoppingCartService.updateItemQuantity(cart, request.getCartItemId(), request.getQuantity());
        if (success) {
            return new ResponseEntity<>("Item quantity successfully changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to change item quantity", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(@RequestHeader("authToken") String authToken, @PathVariable Long cartItemId) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        boolean success = shoppingCartService.removeItemFromCart(cart.getCartId(), cartItemId);
        if (success) {
            return new ResponseEntity<>("Item successfully removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        boolean success = shoppingCartService.clearCart(cart.getCartId());
        if (success) {
            return new ResponseEntity<>("successfully removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-item-selected")
    public ResponseEntity<CartItem> updateItemSelected(@RequestHeader("authToken") String authToken, @RequestParam Long cartItemId, @RequestParam Boolean isSelected) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        CartItem cartItem = cartItemService.updateItemSelected(cart.getCartId(), cartItemId, isSelected);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/selected-items")
    public ResponseEntity<List<CartInfoDTO>> getSelectedItems(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }//NOSONAR

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        List<CartItem> selectedItems = cartItemService.getSelectedItems(cart.getCartId());

        // 将 List<CartItem> 转换为 List<CartInfoDTO>
        List<CartInfoDTO> dtoList = selectedItems.stream()
                .map(shoppingCartService::convertToDTO)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/remove-selected-items")
    public ResponseEntity<String> removeSelectedItems(@RequestHeader("authToken") String authToken) {
        ResponseEntity<ApiResponse<User>> response = userService.getCurrentUserInfo(authToken);
        ApiResponse<User> responseBody = response.getBody();
        if (responseBody == null || !responseBody.isSuccess()) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }

        User user = responseBody.getData();
        if (user == null) {//NOSONAR
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//NOSONAR
        }
        Cart cart = shoppingCartService.getCartByUserId(user.getUserId());
        cartItemService.removeSelectedItems(cart.getCartId());
        return ResponseEntity.ok("Selected items removed successfully");
    }
}
