package com.marketplacehn.service;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.repository.UserRepository;
import com.marketplacehn.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl underTest;
    @Mock
    private UserRepository userRepository;

    private User user;
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USERNAME = "user123";
    private static final int ACTIVE_STATUS = ModelStatus.ACTIVE.getStatusCode();

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId(USER_ID)
                .build();
    }

    @Test
    void findUserById_whenUserExists() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        User expectedUser = underTest.findUserById(USER_ID);

        Assertions.assertEquals(user, expectedUser);
    }

    @Test
    void findUserById_whenUserDoesNotExists() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> underTest.findUserById(USER_ID));
    }

    @Test
    void findUserByUsername_whenUserExists() {
        when(userRepository
                .findByUserNameAndUserStatus(USERNAME, ACTIVE_STATUS))
                .thenReturn(Optional.of(user));

        User expectedUser = underTest.findUserByUserName(USERNAME);

        Assertions.assertEquals(user, expectedUser);
    }

    @Test
    void findUserByUsername_whenUserDoesNotExists() {
        when(userRepository
                .findByUserNameAndUserStatus(USERNAME, ACTIVE_STATUS))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> underTest.findUserByUserName(USERNAME));
    }

    @Test
    void itShouldSetUserStatusToInactive() {
        User userStatusInactive = User.builder()
                .userId(USER_ID)
                .build();

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(userStatusInactive));
        when(userRepository.save(userStatusInactive))
                .thenReturn(userStatusInactive);

        underTest.deleteUserById(USER_ID);

        Assertions.assertEquals(
                userStatusInactive.getUserStatus(),
                ModelStatus.INACTIVE);
    }

    @Test
    void itShouldSaveUser() {
        User persistedUser = User.prepareToPersist(user);

        when(userRepository.save(user))
                .thenReturn(persistedUser);

        User expectedUser = underTest.saveUser(user);

        Assertions.assertEquals(persistedUser, expectedUser);
    }

}
