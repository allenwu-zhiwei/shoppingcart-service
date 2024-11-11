package com.nusiss.shoppingcart.service.impl;


import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.repository.CartItemRepository;
import com.nusiss.shoppingcart.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired//NOSONAR
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