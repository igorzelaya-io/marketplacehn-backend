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
        User user = User.builder()
                .userName("user123")
                .build();
        user = User.prepareToPersist(user);
        underTest.save(user);

        //when
        Optional<User> expectedUser = underTest.findByUserNameAndUserStatus(
                "user123",
                ModelStatus.ACTIVE.getStatusCode()
        );

        //then
        assertTrue(expectedUser.isPresent());
        assertEquals(1, expectedUser.get().getUserStatus().getStatusCode());
    }
}
