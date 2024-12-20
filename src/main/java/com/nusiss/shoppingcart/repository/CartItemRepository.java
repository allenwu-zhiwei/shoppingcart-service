package com.nusiss.shoppingcart.repository;

import com.nusiss.shoppingcart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart_CartIdAndIsSelectedTrue(Long cartId);
    Optional<CartItem> findByCart_CartIdAndCartItemId(Long cartId, Long cartItemId);
}
