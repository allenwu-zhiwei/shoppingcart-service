package com.nusiss.shoppingcart.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.repository.CartItemRepository;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CartItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CartItemServiceImplTest {
    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemServiceImpl cartItemServiceImpl;

    /**
     * Method under test: {@link CartItemServiceImpl#updateItemSelected(Long, Long, Boolean)}
     */
    @Test
    void testUpdateItemSelected() {
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
        when(cartItemRepository.findByCart_CartIdAndCartItemId((Long) any(), (Long) any())).thenReturn(ofResult);
        assertSame(cartItem1, cartItemServiceImpl.updateItemSelected(1L, 1L, true));
        verify(cartItemRepository).save((CartItem) any());
        verify(cartItemRepository).findByCart_CartIdAndCartItemId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#updateItemSelected(Long, Long, Boolean)}
     */
    @Test
    void testUpdateItemSelected2() {
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
        when(cartItemRepository.save((CartItem) any())).thenThrow(new RuntimeException());
        when(cartItemRepository.findByCart_CartIdAndCartItemId((Long) any(), (Long) any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class, () -> cartItemServiceImpl.updateItemSelected(1L, 1L, true));
        verify(cartItemRepository).save((CartItem) any());
        verify(cartItemRepository).findByCart_CartIdAndCartItemId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#updateItemSelected(Long, Long, Boolean)}
     */
    @Test
    void testUpdateItemSelected3() {
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
        when(cartItemRepository.findByCart_CartIdAndCartItemId((Long) any(), (Long) any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> cartItemServiceImpl.updateItemSelected(1L, 1L, true));
        verify(cartItemRepository).findByCart_CartIdAndCartItemId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#getSelectedItems(Long)}
     */
    @Test
    void testGetSelectedItems() {
        ArrayList<CartItem> cartItemList = new ArrayList<>();
        when(cartItemRepository.findByCart_CartIdAndIsSelectedTrue((Long) any())).thenReturn(cartItemList);
        List<CartItem> actualSelectedItems = cartItemServiceImpl.getSelectedItems(1L);
        assertSame(cartItemList, actualSelectedItems);
        assertTrue(actualSelectedItems.isEmpty());
        verify(cartItemRepository).findByCart_CartIdAndIsSelectedTrue((Long) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#getSelectedItems(Long)}
     */
    @Test
    void testGetSelectedItems2() {
        when(cartItemRepository.findByCart_CartIdAndIsSelectedTrue((Long) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> cartItemServiceImpl.getSelectedItems(1L));
        verify(cartItemRepository).findByCart_CartIdAndIsSelectedTrue((Long) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#removeSelectedItems(Long)}
     */
    @Test
    void testRemoveSelectedItems() {
        when(cartItemRepository.findByCart_CartIdAndIsSelectedTrue((Long) any())).thenReturn(new ArrayList<>());
        doNothing().when(cartItemRepository).deleteAll((Iterable<CartItem>) any());
        cartItemServiceImpl.removeSelectedItems(1L);
        verify(cartItemRepository).findByCart_CartIdAndIsSelectedTrue((Long) any());
        verify(cartItemRepository).deleteAll((Iterable<CartItem>) any());
    }

    /**
     * Method under test: {@link CartItemServiceImpl#removeSelectedItems(Long)}
     */
    @Test
    void testRemoveSelectedItems2() {
        when(cartItemRepository.findByCart_CartIdAndIsSelectedTrue((Long) any())).thenThrow(new RuntimeException());
        doThrow(new RuntimeException()).when(cartItemRepository).deleteAll((Iterable<CartItem>) any());
        assertThrows(RuntimeException.class, () -> cartItemServiceImpl.removeSelectedItems(1L));
        verify(cartItemRepository).findByCart_CartIdAndIsSelectedTrue((Long) any());
    }
}

