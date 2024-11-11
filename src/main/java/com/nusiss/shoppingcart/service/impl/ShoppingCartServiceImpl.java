package com.nusiss.shoppingcart.service.impl;

import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;
import com.nusiss.commonservice.feign.UserFeignClient;
import com.nusiss.shoppingcart.dto.CartInfoDTO;
import com.nusiss.shoppingcart.dto.ProductDTO;
import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.exception.CartNotFoundException;
import com.nusiss.shoppingcart.exception.InsufficientStockException;
import com.nusiss.shoppingcart.repository.CartItemRepository;
import com.nusiss.shoppingcart.repository.CartRepository;
import com.nusiss.shoppingcart.service.InventoryServiceClient;
import com.nusiss.shoppingcart.service.ProductServiceClient;
import com.nusiss.shoppingcart.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final CartItemRepository cartItemRepository;
    private final UserFeignClient userFeignClient;

    private static final String CART_NOT_FOUND_MESSAGE = "Cart not found with id: ";
    @Autowired
    public ShoppingCartServiceImpl(CartRepository cartRepository,
                                   ProductServiceClient productServiceClient,
                                   InventoryServiceClient inventoryServiceClient,
                                   CartItemRepository cartItemRepository,
                                   UserFeignClient userFeignClient) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
        this.cartItemRepository = cartItemRepository;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public ResponseEntity<ApiResponse<User>> getUserById(Integer id) {

        return userFeignClient.getUserById(id);
    }

    @Override
    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(String authToken) {

        return userFeignClient.getCurrentUserInfo(authToken);
    }
    @Override
    public Cart createCart(Integer userId, String createUser, String updateUser) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCreateUser(createUser);
        cart.setUpdateUser(updateUser);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public boolean addItemToCart(Cart cart, Long productId, int quantity, double price, String createUser) {
        // check product exist and stock using inventoryServiceClient
        boolean stockAvailable = inventoryServiceClient.check(productId, quantity);
        if (!stockAvailable) {
            throw new InsufficientStockException("Not enough stock for product id: " + productId);
        }

        Optional<CartItem> existingItemOpt = cart.findItemByProductId(productId);
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUpdateUser(createUser);
            existingItem.setUpdateDatetime(LocalDateTime.now());
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(price);
            cartItem.setCreateUser(createUser);
            cartItem.setCreateDatetime(LocalDateTime.now());
            cartItemRepository.save(cartItem);
        }
        return true;
    }


    @Override
    @Transactional
    public boolean updateItemQuantity(Cart cart, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartNotFoundException("Cart item not found with id: " + cartItemId));

        boolean stockAvailable = inventoryServiceClient.check(cartItem.getProductId(), quantity);
        if (!stockAvailable) {
            throw new InsufficientStockException("Not enough stock for product id: " + cartItem.getProductId());
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return true;
    }

    @Override
    @Transactional
    public boolean removeItemFromCart(Long cartId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartNotFoundException("CartItem not found with id: " + cartItemId));

        cartItemRepository.delete(cartItem);
        return true;
    }

    //有问题，无法删除？？？？？
    @Override
    @Transactional
    public boolean clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND_MESSAGE + cartId));

        List<CartItem> cartItems = cart.getCartItems();
        if (!cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                cart.removeCartItem(item);
            }
            cartItemRepository.deleteAll(cartItems);
        }
        return true;
    }

    @Override
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND_MESSAGE + cartId));

        return cart.getCartItems();
    }

    @Override
    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public CartInfoDTO convertToDTO(CartItem cartItem) {
        CartInfoDTO dto = new CartInfoDTO();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setProductId(cartItem.getProductId());
        ResponseEntity<ApiResponse<ProductDTO>> response = productServiceClient.productInfo(cartItem.getProductId());
        ApiResponse<ProductDTO> responseBody = response.getBody();
        if (responseBody != null && responseBody.isSuccess()) {
            ProductDTO productDTO = responseBody.getData();
            if (productDTO != null) {
                dto.setProductName(productDTO.getName());
                dto.setPrice(productDTO.getPrice().doubleValue());
            }
        }
        dto.setQuantity(cartItem.getQuantity());
        dto.setCreateDatetime(cartItem.getCreateDatetime());
        dto.setUpdateDatetime(cartItem.getUpdateDatetime());
        dto.setCreateUser(cartItem.getCreateUser());
        dto.setUpdateUser(cartItem.getUpdateUser());
        return dto;
    }


}
