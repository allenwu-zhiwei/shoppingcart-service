package com.nusiss.shoppingcart;

import com.nusiss.commonservice.entity.User;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.shoppingcart.controller.ShoppingCartController;
import com.nusiss.shoppingcart.entity.Cart;
import com.nusiss.shoppingcart.entity.CartItem;
import com.nusiss.shoppingcart.exception.CartNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        // 模拟返回有效响应
        doReturn(userEntity).when(userService).getCurrentUserInfo(any(String.class));

        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.createCart(anyInt(), any(), any())).thenReturn(cart);

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/create")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateCart_ResponseIsNull() throws Exception {
        // 模拟返回 null 响应
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any(String.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/create")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateCart_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any(String.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/create")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }




    @Test
    void testAddItemToCart() throws Exception {
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
    void testAddItemToCart_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/add-item")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 2, \"price\": 10.0}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAddItemToCart_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(post("/api/v1/cart/add-item")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 2, \"price\": 10.0}"))
                .andExpect(status().isUnauthorized());
    }







    @Test
    void testGetCartItems() throws Exception {
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

        List<CartItem> cartItems = Arrays.asList(item1, item2);
        when(shoppingCartService.getCartItems(anyLong())).thenReturn(cartItems);

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetCartItems_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetCartItems_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
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
    void testUpdateCartItemQuantity_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-quantity")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItemId\": 1, \"quantity\": 5}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateCartItemQuantity_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-quantity")
                        .header("authToken", "dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItemId\": 1, \"quantity\": 5}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRemoveItemFromCart() throws Exception {
        // 模拟 User 对象
        User user = new User();
        ReflectionTestUtils.setField(user, "userId", 1);  // 改为 Integer 类型
        user.setUsername("User1");
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", user);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        // 模拟 userService.getCurrentUserInfo 返回值
        when(userService.getCurrentUserInfo(anyString())).thenReturn(userEntity);

        // 模拟 shoppingCartService.getCartByUserId 返回值
        Cart cart = new Cart();
        cart.setCartId(1L);
        when(shoppingCartService.getCartByUserId(1)).thenReturn(cart);

        // 模拟 shoppingCartService.removeItemFromCart 返回值
        when(shoppingCartService.removeItemFromCart(1L, 1L)).thenReturn(true);

        // 执行测试并验证结果
        mockMvc.perform(delete("/api/v1/cart/remove-item/1")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item successfully removed"));
    }

    @Test
    void testRemoveItemFromCart_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-item/1")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRemoveItemFromCart_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-item/1")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
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
    void testClearCart_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/clear")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testClearCart_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/clear")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
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
    void testUpdateItemSelected_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-selected")
                        .header("authToken", "dummyToken")
                        .param("cartItemId", "1")
                        .param("isSelected", "true"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateItemSelected_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(put("/api/v1/cart/update-item-selected")
                        .header("authToken", "dummyToken")
                        .param("cartItemId", "1")
                        .param("isSelected", "true"))
                .andExpect(status().isUnauthorized());
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
    void testGetSelectedItems_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetSelectedItems_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(get("/api/v1/cart/selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
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

    @Test
    void testRemoveSelectedItems_ResponseBodyIsNull() throws Exception {
        // 模拟返回 null 响应体
        doReturn(new ResponseEntity<>(null, HttpStatus.OK)).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRemoveSelectedItems_UserIsNull() throws Exception {
        // 模拟返回的响应体中包含空的用户数据
        ApiResponse<User> userResponse = new ApiResponse<>(true, "Success", null);
        ResponseEntity<ApiResponse<User>> userEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);
        doReturn(userEntity).when(userService).getCurrentUserInfo(any());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/cart/remove-selected-items")
                        .header("authToken", "dummyToken"))
                .andExpect(status().isUnauthorized());
    }


}
