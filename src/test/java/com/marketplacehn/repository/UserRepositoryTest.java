package com.marketplacehn.repository;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.enums.ModelStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @Test
    void itShouldFindUsernameAndUserStatus() {
        //given
        int status = 1; //ACTIVE
        String username = "admin";
        User user = User.builder()
                .userName(username)
                .build();
        user = User.prepareToPersist(user);
        underTest.save(user);

        //when
        Optional<User> expectedUser = underTest.findByUserNameAndUserStatus(
                username,
                ModelStatus.ACTIVE.getStatusCode()
        );

        //then
        assertNotNull(expectedUser);
        assertTrue(expectedUser.isPresent());
        assertEquals(username, expectedUser.get().getUserId());
        assertEquals(status, expectedUser.get().getUserStatus().getStatusCode());
    }
}
