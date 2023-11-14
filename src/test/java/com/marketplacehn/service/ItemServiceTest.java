package com.marketplacehn.service;

import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.service.impl.ItemServiceImpl;
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
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl underTest;
    private Item item;
    private Item persistedItem;
    private static final String ITEM_ID = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .itemId(ITEM_ID)
                .itemStatus(ModelStatus.ACTIVE)
                .build();
        persistedItem = Item.prepareToPersist(item);
    }
    @Test
    void whenItemExists_returnItem() {
        when(itemRepository.findById(ITEM_ID))
                .thenReturn(Optional.of(item));

        Item expectedItem = underTest.findItemById(ITEM_ID);

        Assertions.assertEquals(item, expectedItem);
    }

    @Test
    void whenItemDoesNotExist_throwException() {
        when(itemRepository.findById(ITEM_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> underTest.findItemById(ITEM_ID));
    }

    @Test
    void itShouldReturnSavedItem() {
        when(itemRepository.save(item))
                .thenReturn(persistedItem);

        Item savedItem = underTest.saveItem(item);

        Assertions.assertEquals(persistedItem, savedItem);
    }

    @Test
    void itShouldSetStatusToInactive() {
        Item itemStatusInactive = Item.builder()
                .itemId(ITEM_ID)
                .build();

        when(itemRepository.findById(ITEM_ID))
                .thenReturn(Optional.of(itemStatusInactive));
        when(itemRepository.save(itemStatusInactive))
                .thenReturn(itemStatusInactive);

        underTest.deleteItemById(ITEM_ID);

        Assertions.assertEquals(
                itemStatusInactive.getItemStatus(),
                ModelStatus.INACTIVE);
    }

}
