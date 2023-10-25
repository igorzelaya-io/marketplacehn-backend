package com.marketplacehn.repository;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BidRepositoryTest {
    @Autowired
    private BidRepository underTest;

    @Test
    void itShouldFindUserAndItemById() {
        //given
        Item item = new Item("item123");
        User user = new User("user123");
        Bid bid = Bid.builder()
                .bidId(UUID.randomUUID().toString())
                .bidValue(BigDecimal.TEN)
                .bidDate(LocalDateTime.now())
                .build();
        bid.setUserBid(user);
        bid.setItem(item);
        underTest.save(bid);

        //when
        List<Object[]> result = underTest.findUserAndItemById("item123", "user123");
        Object[] resultRow = result.get(0);
        Item itemResult = (Item) resultRow[0];
        User userResult = (User) resultRow[1];

        //then
        assertNotNull(result);
        assertEquals(1, result.size());

        assertNotNull(resultRow);
        assertEquals(2, resultRow.length);

        assertNotEquals(0, itemResult.getItemStatus().ordinal());
        assertNotEquals(0, userResult.getUserStatus().ordinal());
    }
}
