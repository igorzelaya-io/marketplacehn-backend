package com.marketplacehn.controller;

import com.marketplacehn.entity.User;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest Controller for User entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<? extends Response<User>> findUserById(@PathVariable final String userId){
        BaseResponse<User> response = new BaseResponse<>();
        final User user = userService.findUserById(userId);
        return response.buildResponseEntity(HttpStatus.OK, "User found successfully.", user);
    }

    @GetMapping
    public ResponseEntity<? extends Response<User>> findUserByEmail(@RequestParam final String userName){
        BaseResponse<User> response = new BaseResponse<>();
        final User retrievedUser = userService.findUserByUserName(userName);
        return response.buildResponseEntity(HttpStatus.OK, "User found successfully.", retrievedUser);
    }

    @PostMapping
    public ResponseEntity<? extends Response<User>> saveUser(@RequestBody final User user){
        BaseResponse<User> response = new BaseResponse<>();
        final User createdUser = userService.saveUser(user);
        return response.buildResponseEntity(HttpStatus.CREATED, "User was created.", createdUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<? extends Response<Void>> deleteUserById(@PathVariable final String userId){
        BaseResponse<Void> response = new BaseResponse<>();
        userService.deleteUserById(userId);
        return response.buildResponseEntity(HttpStatus.OK, "User deleted successfully", null);
    }

}
