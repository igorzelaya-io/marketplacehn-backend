package com.marketplacehn.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class BidRepositoryTest {
    @Autowired
    private BidRepository underTest;

    @Test
    void itShouldFindUserAndItemById() {
        //given
        String itemId = "randomItemId";
        String userId = "randomUserId";

        Object[] expectedIds = {"expectedItemId", "expectedUserId"};
        List<Object[]> expectedResult = List.<Object[]>of(expectedIds);

        //when
        when(underTest.findUserAndItemById(itemId, userId))
                .thenReturn(expectedResult);
        List<Object[]> result = underTest.findUserAndItemById(itemId, userId);
        Object[] resultIds = result.get(0);

        //then
        assertEquals(1, result.size());
        assertEquals(expectedIds[0], resultIds[0]);
        assertNotEquals(expectedIds[1], resultIds[1]);

    }

}
