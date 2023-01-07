package com.marketplacehn.service;

import com.marketplacehn.entity.User;

public interface UserService {

    User findUserById(String userId);

    User findUserByUserName(String userName);

    void deleteUserById( String userId);

    User saveUser(User user);

}
