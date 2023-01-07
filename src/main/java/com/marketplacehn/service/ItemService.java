package com.marketplacehn.service;

import com.marketplacehn.entity.Item;

public interface ItemService {

    Item findItemById(String itemId);

    Item saveItem(Item item);

    void deleteItemById(String itemId);

}
