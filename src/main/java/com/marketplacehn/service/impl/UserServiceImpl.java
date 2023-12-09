package com.marketplacehn.service.impl;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.UserDto;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.mapper.UserMapper;
import com.marketplacehn.repository.UserRepository;
import com.marketplacehn.service.UserService;
import com.marketplacehn.utils.SortingUtils;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final SortingUtils sortingUtils;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, SortingUtils sortingUtils) {
        this.userRepo = userRepository;
        this.sortingUtils = sortingUtils;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Override
    public User findUserById(final String userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Page<UserDto> findPaginatedUsers(int page, int size, String[] sort) {
        final List<Sort.Order> sortOrder = sortingUtils.getSortingOrder(sort);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        return userMapper.pageOfEntitiesToDtos(userRepo.findAll(pageable));
    }

    @Override
    public User findUserByUserName(final String userName) {
        return userRepo
                .findByUserNameAndUserStatus(userName, ModelStatus.ACTIVE.getStatusCode())
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public void deleteUserById(final String userId) {
        User user = findUserById(userId);
        user.setUserStatus(ModelStatus.INACTIVE);
        userRepo.save(user);
    }

    @Override
    public User saveUser(User user) {
        User.prepareToPersist(user);
        return userRepo.save(user);
    }
}
