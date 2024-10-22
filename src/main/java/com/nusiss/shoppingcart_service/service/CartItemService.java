package com.nusiss.shoppingcart_service.service;

import com.nusiss.shoppingcart_service.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem updateItemSelected(Long cartId, Long cartItemId, Boolean isSelected);

    List<CartItem> getSelectedItems(Long cartId);

    void removeSelectedItems(Long cartId);
}
