package com.nusiss.shoppingcart.service;

import org.springframework.http.ResponseEntity;
import com.nusiss.commonservice.config.ApiResponse;
import com.nusiss.commonservice.entity.User;

public interface UserService {

    public ResponseEntity<ApiResponse<User>> getUserById(Integer id);

    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(String authToken);

}
