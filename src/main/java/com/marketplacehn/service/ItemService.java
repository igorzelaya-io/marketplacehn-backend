package com.marketplacehn.service;

import com.marketplacehn.entity.Item;
import org.springframework.data.domain.Page;

public interface ItemService {

    Item findItemById(String itemId);

    Item saveItem(Item item);

    void deleteItemById(String itemId);

    Page<Item> findAllItemsByUserId(String userId, int page, int size, String[] sort);

}
