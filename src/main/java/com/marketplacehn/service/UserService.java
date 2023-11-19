package com.marketplacehn.service;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User findUserById(String userId);

    Page<UserDto> findPaginatedUsers(int page, int size, String[] sort);

    User findUserByUserName(String userName);

    void deleteUserById( String userId);

    User saveUser(User user);

}
