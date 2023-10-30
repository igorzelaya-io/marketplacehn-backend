package com.marketplacehn.service;

import com.marketplacehn.entity.User;
import com.marketplacehn.repository.UserRepository;
import com.marketplacehn.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService underTest;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void itShouldFindUsernameAndStatusCode() {
        //given
        int status = 1;
        User user = new User();
        String username = "testUser";
        user.setUserName(username);

        //when
        when(userRepository.findByUserNameAndUserStatus(
                username,
                status
        )).thenReturn(Optional.of(user));
        User expectedUser = underTest.findUserByUserName(username);

        //then
        assertNotNull(expectedUser);
        assertDoesNotThrow(() -> {
            assertEquals(user, expectedUser);
            assertEquals(username, expectedUser.getUserName());
            assertEquals(status, expectedUser.getUserStatus().getStatusCode());
        });
    }

}
