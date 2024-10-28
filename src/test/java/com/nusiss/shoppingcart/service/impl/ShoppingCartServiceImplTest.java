package com.nusiss.shoppingcart.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nusiss.commonservice.config.ApiResponse;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShoppingCartServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ShoppingCartServiceImplTest {
    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private InventoryServiceClient inventoryServiceClient;

    @MockBean
    private ProductServiceClient productServiceClient;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @MockBean
    private UserFeignClient userFeignClient;

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkFeignClients()}
     */
    @Test
    void testCheckFeignClients() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ShoppingCartServiceImpl.cartItemRepository
        //     ShoppingCartServiceImpl.cartRepository
        //     ShoppingCartServiceImpl.inventoryServiceClient
        //     ShoppingCartServiceImpl.productServiceClient
        //     ShoppingCartServiceImpl.userFeignClient

        ShoppingCartServiceImpl shoppingCartServiceImpl = new ShoppingCartServiceImpl(mock(CartRepository.class),
                mock(ProductServiceClient.class), mock(InventoryServiceClient.class), mock(CartItemRepository.class), null);
        shoppingCartServiceImpl.checkFeignClients();
        assertNull(shoppingCartServiceImpl.getCartByUserId(1));
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkFeignClients()}
     */
    @Test
    void testCheckFeignClients2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ShoppingCartServiceImpl.cartItemRepository
        //     ShoppingCartServiceImpl.cartRepository
        //     ShoppingCartServiceImpl.inventoryServiceClient
        //     ShoppingCartServiceImpl.productServiceClient
        //     ShoppingCartServiceImpl.userFeignClient

        ShoppingCartServiceImpl shoppingCartServiceImpl = new ShoppingCartServiceImpl(mock(CartRepository.class), null,
                mock(InventoryServiceClient.class), mock(CartItemRepository.class), null);
        shoppingCartServiceImpl.checkFeignClients();
        assertNull(shoppingCartServiceImpl.getCartByUserId(1));
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkFeignClients()}
     */
    @Test
    void testCheckFeignClients3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ShoppingCartServiceImpl.cartItemRepository
        //     ShoppingCartServiceImpl.cartRepository
        //     ShoppingCartServiceImpl.inventoryServiceClient
        //     ShoppingCartServiceImpl.productServiceClient
        //     ShoppingCartServiceImpl.userFeignClient

        ShoppingCartServiceImpl shoppingCartServiceImpl = new ShoppingCartServiceImpl(mock(CartRepository.class),
                mock(ProductServiceClient.class), null, mock(CartItemRepository.class), null);
        shoppingCartServiceImpl.checkFeignClients();
        assertNull(shoppingCartServiceImpl.getCartByUserId(1));
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkFeignClients()}
     */
    @Test
    void testCheckFeignClients4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ShoppingCartServiceImpl.cartItemRepository
        //     ShoppingCartServiceImpl.cartRepository
        //     ShoppingCartServiceImpl.inventoryServiceClient
        //     ShoppingCartServiceImpl.productServiceClient
        //     ShoppingCartServiceImpl.userFeignClient

        ShoppingCartServiceImpl shoppingCartServiceImpl = new ShoppingCartServiceImpl(mock(CartRepository.class),
                mock(ProductServiceClient.class), mock(InventoryServiceClient.class), mock(CartItemRepository.class),
                mock(UserFeignClient.class));
        shoppingCartServiceImpl.checkFeignClients();
        assertNull(shoppingCartServiceImpl.getCartByUserId(1));
        assertNull(shoppingCartServiceImpl.getCurrentUserInfo("ABC123"));
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getUserById(Integer)}
     */
    @Test
    void testGetUserById() {
        when(userFeignClient.getUserById((Integer) any())).thenReturn(null);
        assertNull(shoppingCartServiceImpl.getUserById(1));
        verify(userFeignClient).getUserById((Integer) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getUserById(Integer)}
     */
    @Test
    void testGetUserById2() {
        when(userFeignClient.getUserById((Integer) any())).thenThrow(new InsufficientStockException("An error occurred"));
        assertThrows(InsufficientStockException.class, () -> shoppingCartServiceImpl.getUserById(1));
        verify(userFeignClient).getUserById((Integer) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCurrentUserInfo(String)}
     */
    @Test
    void testGetCurrentUserInfo() {
        when(userFeignClient.getCurrentUserInfo((String) any())).thenReturn(null);
        assertNull(shoppingCartServiceImpl.getCurrentUserInfo("ABC123"));
        verify(userFeignClient).getCurrentUserInfo((String) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCurrentUserInfo(String)}
     */
    @Test
    void testGetCurrentUserInfo2() {
        when(userFeignClient.getCurrentUserInfo((String) any()))
                .thenThrow(new InsufficientStockException("An error occurred"));
        assertThrows(InsufficientStockException.class, () -> shoppingCartServiceImpl.getCurrentUserInfo("ABC123"));
        verify(userFeignClient).getCurrentUserInfo((String) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#createCart(Integer, String, String)}
     */
    @Test
    void testCreateCart() {
        Cart cart = new Cart();
        when(cartRepository.save((Cart) any())).thenReturn(cart);
        assertSame(cart, shoppingCartServiceImpl.createCart(1, "Create User", "2020-03-01"));
        verify(cartRepository).save((Cart) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#createCart(Integer, String, String)}
     */
    @Test
    void testCreateCart2() {
        when(cartRepository.save((Cart) any())).thenThrow(new InsufficientStockException("An error occurred"));
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.createCart(1, "Create User", "2020-03-01"));
        verify(cartRepository).save((Cart) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    void testAddItemToCart() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem);
        assertTrue(shoppingCartServiceImpl.addItemToCart(new Cart(), 1L, 2, 10.0d, "Create User"));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    void testAddItemToCart2() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);
        when(cartItemRepository.save((CartItem) any())).thenThrow(new InsufficientStockException("An error occurred"));
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.addItemToCart(new Cart(), 1L, 2, 10.0d, "Create User"));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    void testAddItemToCart3() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(false);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.addItemToCart(new Cart(), 1L, 2, 10.0d, "Create User"));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddItemToCart4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.nusiss.shoppingcart_service.entity.Cart.findItemByProductId(java.lang.Long)" because "cart" is null
        //       at com.nusiss.shoppingcart_service.service.impl.ShoppingCartServiceImpl.addItemToCart(ShoppingCartServiceImpl.java:88)
        //   See https://diff.blue/R013 to resolve this issue.

        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem);
        shoppingCartServiceImpl.addItemToCart(null, 1L, 2, 10.0d, "Create User");
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    void testAddItemToCart5() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        Cart cart = mock(Cart.class);
        when(cart.findItemByProductId((Long) any())).thenReturn(Optional.of(cartItem1));
        assertTrue(shoppingCartServiceImpl.addItemToCart(cart, 1L, 2, 10.0d, "Create User"));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
        verify(cart).findItemByProductId((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#addItemToCart(Cart, Long, int, double, String)}
     */
    @Test
    void testAddItemToCart6() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem);
        CartItem cartItem1 = mock(CartItem.class);
        when(cartItem1.getQuantity()).thenReturn(1);
        doNothing().when(cartItem1).setCart((Cart) any());
        doNothing().when(cartItem1).setCartItemId((Long) any());
        doNothing().when(cartItem1).setCreateDatetime((LocalDateTime) any());
        doNothing().when(cartItem1).setCreateUser((String) any());
        doNothing().when(cartItem1).setIsSelected((Boolean) any());
        doNothing().when(cartItem1).setPrice(anyDouble());
        doNothing().when(cartItem1).setProductId((Long) any());
        doNothing().when(cartItem1).setQuantity(anyInt());
        doNothing().when(cartItem1).setUpdateDatetime((LocalDateTime) any());
        doNothing().when(cartItem1).setUpdateUser((String) any());
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        Cart cart = mock(Cart.class);
        when(cart.findItemByProductId((Long) any())).thenReturn(Optional.of(cartItem1));
        assertTrue(shoppingCartServiceImpl.addItemToCart(cart, 1L, 2, 10.0d, "Create User"));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
        verify(cart).findItemByProductId((Long) any());
        verify(cartItem1).getQuantity();
        verify(cartItem1).setCart((Cart) any());
        verify(cartItem1).setCartItemId((Long) any());
        verify(cartItem1).setCreateDatetime((LocalDateTime) any());
        verify(cartItem1).setCreateUser((String) any());
        verify(cartItem1).setIsSelected((Boolean) any());
        verify(cartItem1).setPrice(anyDouble());
        verify(cartItem1).setProductId((Long) any());
        verify(cartItem1, atLeast(1)).setQuantity(anyInt());
        verify(cartItem1, atLeast(1)).setUpdateDatetime((LocalDateTime) any());
        verify(cartItem1, atLeast(1)).setUpdateUser((String) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#updateItemQuantity(Cart, Long, int)}
     */
    @Test
    void testUpdateItemQuantity() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem1);
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(shoppingCartServiceImpl.updateItemQuantity(new Cart(), 1L, 1));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
        verify(cartItemRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#updateItemQuantity(Cart, Long, int)}
     */
    @Test
    void testUpdateItemQuantity2() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);
        when(cartItemRepository.save((CartItem) any())).thenThrow(new InsufficientStockException("An error occurred"));
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.updateItemQuantity(new Cart(), 1L, 1));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
        verify(cartItemRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#updateItemQuantity(Cart, Long, int)}
     */
    @Test
    void testUpdateItemQuantity3() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(false);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem1);
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.updateItemQuantity(new Cart(), 1L, 1));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#updateItemQuantity(Cart, Long, int)}
     */
    @Test
    void testUpdateItemQuantity4() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(true);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProductId()).thenReturn(1L);
        doNothing().when(cartItem).setCart((Cart) any());
        doNothing().when(cartItem).setCartItemId((Long) any());
        doNothing().when(cartItem).setCreateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setCreateUser((String) any());
        doNothing().when(cartItem).setIsSelected((Boolean) any());
        doNothing().when(cartItem).setPrice(anyDouble());
        doNothing().when(cartItem).setProductId((Long) any());
        doNothing().when(cartItem).setQuantity(anyInt());
        doNothing().when(cartItem).setUpdateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setUpdateUser((String) any());
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem1);
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(shoppingCartServiceImpl.updateItemQuantity(new Cart(), 1L, 1));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).save((CartItem) any());
        verify(cartItemRepository).findById((Long) any());
        verify(cartItem).getProductId();
        verify(cartItem).setCart((Cart) any());
        verify(cartItem).setCartItemId((Long) any());
        verify(cartItem).setCreateDatetime((LocalDateTime) any());
        verify(cartItem).setCreateUser((String) any());
        verify(cartItem).setIsSelected((Boolean) any());
        verify(cartItem).setPrice(anyDouble());
        verify(cartItem).setProductId((Long) any());
        verify(cartItem, atLeast(1)).setQuantity(anyInt());
        verify(cartItem).setUpdateDatetime((LocalDateTime) any());
        verify(cartItem).setUpdateUser((String) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#updateItemQuantity(Cart, Long, int)}
     */
    @Test
    void testUpdateItemQuantity5() {
        when(inventoryServiceClient.check((Long) any(), anyInt())).thenReturn(false);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProductId()).thenReturn(1L);
        doNothing().when(cartItem).setCart((Cart) any());
        doNothing().when(cartItem).setCartItemId((Long) any());
        doNothing().when(cartItem).setCreateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setCreateUser((String) any());
        doNothing().when(cartItem).setIsSelected((Boolean) any());
        doNothing().when(cartItem).setPrice(anyDouble());
        doNothing().when(cartItem).setProductId((Long) any());
        doNothing().when(cartItem).setQuantity(anyInt());
        doNothing().when(cartItem).setUpdateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setUpdateUser((String) any());
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(new Cart());
        cartItem1.setCartItemId(1L);
        cartItem1.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setCreateUser("Create User");
        cartItem1.setIsSelected(true);
        cartItem1.setPrice(10.0d);
        cartItem1.setProductId(1L);
        cartItem1.setQuantity(1);
        cartItem1.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem1.setUpdateUser("2020-03-01");
        when(cartItemRepository.save((CartItem) any())).thenReturn(cartItem1);
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartServiceImpl.updateItemQuantity(new Cart(), 1L, 1));
        verify(inventoryServiceClient).check((Long) any(), anyInt());
        verify(cartItemRepository).findById((Long) any());
        verify(cartItem, atLeast(1)).getProductId();
        verify(cartItem).setCart((Cart) any());
        verify(cartItem).setCartItemId((Long) any());
        verify(cartItem).setCreateDatetime((LocalDateTime) any());
        verify(cartItem).setCreateUser((String) any());
        verify(cartItem).setIsSelected((Boolean) any());
        verify(cartItem).setPrice(anyDouble());
        verify(cartItem).setProductId((Long) any());
        verify(cartItem).setQuantity(anyInt());
        verify(cartItem).setUpdateDatetime((LocalDateTime) any());
        verify(cartItem).setUpdateUser((String) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#removeItemFromCart(Long, Long)}
     */
    @Test
    void testRemoveItemFromCart() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.of(new Cart()));

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);
        doNothing().when(cartItemRepository).delete((CartItem) any());
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(shoppingCartServiceImpl.removeItemFromCart(1L, 1L));
        verify(cartRepository).findById((Long) any());
        verify(cartItemRepository).findById((Long) any());
        verify(cartItemRepository).delete((CartItem) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#removeItemFromCart(Long, Long)}
     */
    @Test
    void testRemoveItemFromCart2() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.of(new Cart()));

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);
        doThrow(new InsufficientStockException("An error occurred")).when(cartItemRepository).delete((CartItem) any());
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InsufficientStockException.class, () -> shoppingCartServiceImpl.removeItemFromCart(1L, 1L));
        verify(cartRepository).findById((Long) any());
        verify(cartItemRepository).findById((Long) any());
        verify(cartItemRepository).delete((CartItem) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#removeItemFromCart(Long, Long)}
     */
    @Test
    void testRemoveItemFromCart3() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.empty());

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        Optional<CartItem> ofResult = Optional.of(cartItem);
        doNothing().when(cartItemRepository).delete((CartItem) any());
        when(cartItemRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(CartNotFoundException.class, () -> shoppingCartServiceImpl.removeItemFromCart(1L, 1L));
        verify(cartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#removeItemFromCart(Long, Long)}
     */
    @Test
    void testRemoveItemFromCart4() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.of(new Cart()));
        doNothing().when(cartItemRepository).delete((CartItem) any());
        when(cartItemRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> shoppingCartServiceImpl.removeItemFromCart(1L, 1L));
        verify(cartRepository).findById((Long) any());
        verify(cartItemRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#clearCart(Long)}
     */
    @Test
    void testClearCart() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.of(new Cart()));
        assertTrue(shoppingCartServiceImpl.clearCart(1L));
        verify(cartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#clearCart(Long)}
     */
    @Test
    void testClearCart2() {
        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(new ArrayList<>());
        Optional<Cart> ofResult = Optional.of(cart);
        when(cartRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(shoppingCartServiceImpl.clearCart(1L));
        verify(cartRepository).findById((Long) any());
        verify(cart).getCartItems();
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#clearCart(Long)}
     */
    @Test
    void testClearCart3() {
        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");

        ArrayList<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        Cart cart = mock(Cart.class);
        doNothing().when(cart).removeCartItem((CartItem) any());
        when(cart.getCartItems()).thenReturn(cartItemList);
        Optional<Cart> ofResult = Optional.of(cart);
        when(cartRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(cartItemRepository).deleteAll((Iterable<CartItem>) any());
        assertTrue(shoppingCartServiceImpl.clearCart(1L));
        verify(cartRepository).findById((Long) any());
        verify(cart).getCartItems();
        verify(cart).removeCartItem((CartItem) any());
        verify(cartItemRepository).deleteAll((Iterable<CartItem>) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#clearCart(Long)}
     */
    @Test
    void testClearCart4() {
        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");

        ArrayList<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        Cart cart = mock(Cart.class);
        doNothing().when(cart).removeCartItem((CartItem) any());
        when(cart.getCartItems()).thenReturn(cartItemList);
        Optional<Cart> ofResult = Optional.of(cart);
        when(cartRepository.findById((Long) any())).thenReturn(ofResult);
        doThrow(new InsufficientStockException("An error occurred")).when(cartItemRepository)
                .deleteAll((Iterable<CartItem>) any());
        assertThrows(InsufficientStockException.class, () -> shoppingCartServiceImpl.clearCart(1L));
        verify(cartRepository).findById((Long) any());
        verify(cart).getCartItems();
        verify(cart).removeCartItem((CartItem) any());
        verify(cartItemRepository).deleteAll((Iterable<CartItem>) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#clearCart(Long)}
     */
    @Test
    void testClearCart5() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.empty());
        doNothing().when(cartItemRepository).deleteAll((Iterable<CartItem>) any());
        assertThrows(CartNotFoundException.class, () -> shoppingCartServiceImpl.clearCart(1L));
        verify(cartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartItems(Long)}
     */
    @Test
    void testGetCartItems() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.of(new Cart()));
        assertTrue(shoppingCartServiceImpl.getCartItems(1L).isEmpty());
        verify(cartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartItems(Long)}
     */
    @Test
    void testGetCartItems2() {
        Cart cart = mock(Cart.class);
        ArrayList<CartItem> cartItemList = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(cartItemList);
        Optional<Cart> ofResult = Optional.of(cart);
        when(cartRepository.findById((Long) any())).thenReturn(ofResult);
        List<CartItem> actualCartItems = shoppingCartServiceImpl.getCartItems(1L);
        assertSame(cartItemList, actualCartItems);
        assertTrue(actualCartItems.isEmpty());
        verify(cartRepository).findById((Long) any());
        verify(cart).getCartItems();
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartItems(Long)}
     */
    @Test
    void testGetCartItems3() {
        when(cartRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> shoppingCartServiceImpl.getCartItems(1L));
        verify(cartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartItems(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCartItems4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.nusiss.shoppingcart_service.exception.InsufficientStockException: An error occurred
        //       at com.nusiss.shoppingcart_service.entity.Cart.getCartItems(Cart.java:88)
        //       at com.nusiss.shoppingcart_service.service.impl.ShoppingCartServiceImpl.getCartItems(ShoppingCartServiceImpl.java:161)
        //   See https://diff.blue/R013 to resolve this issue.

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenThrow(new InsufficientStockException("An error occurred"));
        Optional<Cart> ofResult = Optional.of(cart);
        when(cartRepository.findById((Long) any())).thenReturn(ofResult);
        shoppingCartServiceImpl.getCartItems(1L);
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartByUserId(Integer)}
     */
    @Test
    void testGetCartByUserId() {
        Cart cart = new Cart();
        when(cartRepository.findByUserId((Integer) any())).thenReturn(Optional.of(cart));
        assertSame(cart, shoppingCartServiceImpl.getCartByUserId(1));
        verify(cartRepository).findByUserId((Integer) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartByUserId(Integer)}
     */
    @Test
    void testGetCartByUserId2() {
        when(cartRepository.findByUserId((Integer) any())).thenThrow(new InsufficientStockException("An error occurred"));
        assertThrows(InsufficientStockException.class, () -> shoppingCartServiceImpl.getCartByUserId(1));
        verify(cartRepository).findByUserId((Integer) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#convertToDTO(CartItem)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConvertToDTO() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.http.ResponseEntity.getBody()" because "response" is null
        //       at com.nusiss.shoppingcart_service.service.impl.ShoppingCartServiceImpl.convertToDTO(ShoppingCartServiceImpl.java:175)
        //   See https://diff.blue/R013 to resolve this issue.

        when(productServiceClient.productInfo((Long) any())).thenReturn(null);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        shoppingCartServiceImpl.convertToDTO(cartItem);
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#convertToDTO(CartItem)}
     */
    @Test
    void testConvertToDTO2() {
        ResponseEntity<ApiResponse<ProductDTO>> responseEntity = (ResponseEntity<ApiResponse<ProductDTO>>) mock(
                ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new ApiResponse<>());
        when(productServiceClient.productInfo((Long) any())).thenReturn(responseEntity);

        CartItem cartItem = new CartItem();
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        CartInfoDTO actualConvertToDTOResult = shoppingCartServiceImpl.convertToDTO(cartItem);
        assertEquals(1L, actualConvertToDTOResult.getCartItemId().longValue());
        assertEquals("2020-03-01", actualConvertToDTOResult.getUpdateUser());
        assertEquals(1, actualConvertToDTOResult.getQuantity());
        assertEquals("01:01", actualConvertToDTOResult.getUpdateDatetime().toLocalTime().toString());
        assertEquals(1L, actualConvertToDTOResult.getProductId().longValue());
        assertEquals("Create User", actualConvertToDTOResult.getCreateUser());
        assertEquals("0001-01-01", actualConvertToDTOResult.getCreateDatetime().toLocalDate().toString());
        verify(productServiceClient).productInfo((Long) any());
        verify(responseEntity, atLeast(1)).getBody();
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#convertToDTO(CartItem)}
     */
    @Test
    void testConvertToDTO3() {
        ResponseEntity<ApiResponse<ProductDTO>> responseEntity = (ResponseEntity<ApiResponse<ProductDTO>>) mock(
                ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new ApiResponse<>());
        when(productServiceClient.productInfo((Long) any())).thenReturn(responseEntity);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getQuantity()).thenReturn(1);
        when(cartItem.getCartItemId()).thenReturn(1L);
        when(cartItem.getProductId()).thenReturn(1L);
        when(cartItem.getCreateUser()).thenReturn("Create User");
        when(cartItem.getUpdateUser()).thenReturn("2020-03-01");
        when(cartItem.getCreateDatetime()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(cartItem.getUpdateDatetime()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(cartItem).setCart((Cart) any());
        doNothing().when(cartItem).setCartItemId((Long) any());
        doNothing().when(cartItem).setCreateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setCreateUser((String) any());
        doNothing().when(cartItem).setIsSelected((Boolean) any());
        doNothing().when(cartItem).setPrice(anyDouble());
        doNothing().when(cartItem).setProductId((Long) any());
        doNothing().when(cartItem).setQuantity(anyInt());
        doNothing().when(cartItem).setUpdateDatetime((LocalDateTime) any());
        doNothing().when(cartItem).setUpdateUser((String) any());
        cartItem.setCart(new Cart());
        cartItem.setCartItemId(1L);
        cartItem.setCreateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setCreateUser("Create User");
        cartItem.setIsSelected(true);
        cartItem.setPrice(10.0d);
        cartItem.setProductId(1L);
        cartItem.setQuantity(1);
        cartItem.setUpdateDatetime(LocalDateTime.of(1, 1, 1, 1, 1));
        cartItem.setUpdateUser("2020-03-01");
        CartInfoDTO actualConvertToDTOResult = shoppingCartServiceImpl.convertToDTO(cartItem);
        assertEquals(1L, actualConvertToDTOResult.getCartItemId().longValue());
        assertEquals("2020-03-01", actualConvertToDTOResult.getUpdateUser());
        assertEquals(1, actualConvertToDTOResult.getQuantity());
        assertEquals("01:01", actualConvertToDTOResult.getUpdateDatetime().toLocalTime().toString());
        assertEquals(1L, actualConvertToDTOResult.getProductId().longValue());
        assertEquals("Create User", actualConvertToDTOResult.getCreateUser());
        assertEquals("0001-01-01", actualConvertToDTOResult.getCreateDatetime().toLocalDate().toString());
        verify(productServiceClient).productInfo((Long) any());
        verify(responseEntity, atLeast(1)).getBody();
        verify(cartItem).getQuantity();
        verify(cartItem).getCartItemId();
        verify(cartItem, atLeast(1)).getProductId();
        verify(cartItem).getCreateUser();
        verify(cartItem).getUpdateUser();
        verify(cartItem).getCreateDatetime();
        verify(cartItem).getUpdateDatetime();
        verify(cartItem).setCart((Cart) any());
        verify(cartItem).setCartItemId((Long) any());
        verify(cartItem).setCreateDatetime((LocalDateTime) any());
        verify(cartItem).setCreateUser((String) any());
        verify(cartItem).setIsSelected((Boolean) any());
        verify(cartItem).setPrice(anyDouble());
        verify(cartItem).setProductId((Long) any());
        verify(cartItem).setQuantity(anyInt());
        verify(cartItem).setUpdateDatetime((LocalDateTime) any());
        verify(cartItem).setUpdateUser((String) any());
    }
}

