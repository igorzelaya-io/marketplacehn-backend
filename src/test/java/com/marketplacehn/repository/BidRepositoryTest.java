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
        int status = 0; //INACTIVE
        String itemId = "item123";
        String userId = "user123";
        Item item = new Item(itemId);
        User user = new User(userId);

        Bid bid = new Bid();
        bid.setUserBid(user);
        bid.setItem(item);
        underTest.save(bid);

        //when
        List<Object[]> result = underTest.findUserAndItemById(itemId, userId);
        Object[] resultRow = result.get(0);
        Item itemResult = (Item) resultRow[0];
        User userResult = (User) resultRow[1];

        //then
        assertNotNull(result);
        assertEquals(1, result.size());

        assertNotNull(resultRow);
        assertEquals(2, resultRow.length);

        assertNotEquals(status, itemResult.getItemStatus().getStatusCode());
        assertNotEquals(status, userResult.getUserStatus().getStatusCode());
    }
}
