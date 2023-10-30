package com.marketplacehn.repository;

import com.marketplacehn.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository underTest;

    @Test
    void itShouldFindByActiveStatus() {
        //given
        int status = 1; //ACTIVE
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Item> itemList = sampleItems();
        underTest.saveAll(itemList);

        //when
        Page<Item> pageResult = underTest.findByActiveStatus(pageable);

        //then
        assertNotNull(pageResult);
        assertEquals(itemList.size(), pageResult.getNumberOfElements());
        assertTrue(pageResult.getContent().containsAll(itemList));
        pageResult.getContent().forEach(item -> {
            assertEquals(status, item.getItemStatus().getStatusCode());
        });
    }

    @Test
    void itShouldFindActiveItemById() {
        //given
        int status = 1; //ACTIVE
        String itemId = "item456";
        Item item = new Item(itemId);
        underTest.save(item);

        //when
        Optional<Item> itemResult = underTest.findActiveItemById(itemId);

        //then
        assertNotNull(itemResult);
        assertTrue(itemResult.isPresent());
        assertEquals(itemId, itemResult.get().getItemId());
        assertEquals(status, itemResult.get().getItemStatus().getStatusCode());
    }

    private List<Item> sampleItems() {
        List<Item> sampleItems = new ArrayList<>();
        sampleItems.add(new Item("item123"));
        sampleItems.add(new Item("item321"));
        return sampleItems;
    }
}
