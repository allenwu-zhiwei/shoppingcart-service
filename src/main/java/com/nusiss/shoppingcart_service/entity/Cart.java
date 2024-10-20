package com.nusiss.shoppingcart_service.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "cart", schema = "nusmall_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String createUser;

    @Column(nullable = false)
    private String updateUser;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDatetime = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updateDatetime = LocalDateTime.now();

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItems;

    // Getters and setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Optional<CartItem> findItemByProductId(Long productId) {
        return cartItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
    }
}