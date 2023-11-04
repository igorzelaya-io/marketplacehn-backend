package com.marketplacehn.service.impl;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.repository.UserRepository;
import com.marketplacehn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public User findUserById(final String userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException());
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
        userRepo.deleteById(userId);
    }

    @Override
    public User saveUser(User user) {
        User.prepareToPersist(user);
        return userRepo.save(user);
    }
}
