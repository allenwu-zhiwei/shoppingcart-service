package com.nusiss.shoppingcart_service.service.impl;


import com.nusiss.shoppingcart_service.entity.CartItem;
import com.nusiss.shoppingcart_service.repository.CartItemRepository;
import com.nusiss.shoppingcart_service.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem updateItemSelected(Long cartId, Long cartItemId, Boolean isSelected) {
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndCartItemId(cartId, cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + cartItemId + " in cart id: " + cartId));

        cartItem.setIsSelected(isSelected);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getSelectedItems(Long cartId) {
        return cartItemRepository.findByCart_CartIdAndIsSelectedTrue(cartId);
    }

    @Override
    public void removeSelectedItems(Long cartId) {
        List<CartItem> selectedItems = cartItemRepository.findByCart_CartIdAndIsSelectedTrue(cartId);
        cartItemRepository.deleteAll(selectedItems);
    }
}