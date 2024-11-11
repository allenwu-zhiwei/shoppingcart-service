package com.nusiss.shoppingcart;

import com.nusiss.commonservice.entity.User;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.shoppingcart.controller.ShoppingCartController;
import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.service.CartItemService;
import com.nusiss.shoppingcart.service.ShoppingCartService;
import com.nusiss.shoppingcart.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingcartServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateCart() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        // 使用反射设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        // 使用 doReturn 模拟返回值
        doReturn(userEntity).when(userService).getCurrentUserInfo(any(String.class));

        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.createCart(anyInt(), any(), any())).thenReturn(cart);  // 根据方法签名调整为 anyInt()

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/create")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



    @Test
    void testAddItemToCart() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        // 使用反射设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);
        when(shoppingCartService.addItemToCart(any(Cart.class), anyLong(), anyInt(), anyDouble(), anyString())).thenReturn(true);


        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/add-item")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 2, \"price\": 10.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item successfully added to cart"));
    }

    @Test
    void testGetCartItems() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        // 使用反射设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);

        CartItem item1 = new CartItem();
        item1.setCartItemId(1L);
        item1.setProductId(101L);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setCartItemId(2L);
        item2.setProductId(102L);
        item2.setQuantity(3);

        List<CartItem> cartItems = Arrays.asList(item1, item2);
        when(shoppingCartService.getCartItems(anyLong())).thenReturn(cartItems);

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateCartItemQuantity() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);
        when(shoppingCartService.updateItemQuantity(any(Cart.class), anyLong(), anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-quantity")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItemId\": 1, \"quantity\": 5}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item quantity successfully changed"));
    }

    @Test
    void testRemoveItemFromCart() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);
        when(shoppingCartService.removeItemFromCart(anyLong(), anyLong())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-item/1")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item successfully removed"));
    }

    @Test
    void testClearCart() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);
        when(shoppingCartService.clearCart(anyLong())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/clear")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully removed"));
    }

    @Test
    void testUpdateItemSelected() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        when(cartItemService.updateItemSelected(anyLong(), anyLong(), anyBoolean())).thenReturn(cartItem);

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-selected")
                        .header("authToken", "dummyToken")
                        .param("cartItemId", "1")
                        .param("isSelected", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetSelectedItems() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);

        CartItem item1 = new CartItem();
        item1.setCartItemId(1L);
        item1.setProductId(101L);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setCartItemId(2L);
        item2.setProductId(102L);
        item2.setQuantity(3);

        List<CartItem> selectedItems = Arrays.asList(item1, item2);
        when(cartItemService.getSelectedItems(anyLong())).thenReturn(selectedItems);

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testRemoveSelectedItems() throws Exception {
        // Arrange
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 使用 Integer 类型设置 userId
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        doReturn(userEntity).when(userService).getCurrentUserInfo(any());
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(anyInt())).thenReturn(cart);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Selected items removed successfully"));
    }

}
