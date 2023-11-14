package com.marketplacehn.controller;

import com.marketplacehn.entity.Item;
import com.marketplacehn.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ItemController.class)
class ItemControllerTest extends AbstractTestController {
    @MockBean
    ItemService underTest;
    Item item;
    private final static String ITEM_ID = UUID.randomUUID().toString();
    private final static String PREFIX = "/api/v1/items";

    @BeforeEach()
    void setUp() {
        item = Item.builder()
                .itemId(ITEM_ID)
                .build();
    }

    @Test
    void itShouldFindItemById() throws Exception {
        when(underTest.findItemById(ITEM_ID))
                .thenReturn(item);
        ResultActions result = doRequestFindItemById();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.itemId").value(ITEM_ID));
    }

    @Test
    void itShouldSaveItem() throws Exception {
        when(underTest.saveItem(item))
                .thenReturn(new Item());
        ResultActions result = doRequestSaveItem();
        result
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldDeleteItemById() throws Exception {
        doNothing().when(underTest)
                .deleteItemById(ITEM_ID);
        ResultActions result = doRequestDeleteItemById();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item deleted successfully."));
    }

    private ResultActions doRequestFindItemById() throws Exception {
        return mvc.perform(get(PREFIX + "/{itemId}", ITEM_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveItem() throws Exception {
        return mvc.perform(post(PREFIX, item)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(item)));
    }

    private ResultActions doRequestDeleteItemById() throws Exception {
        return mvc
                .perform(delete(PREFIX + "/{itemId}", ITEM_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
