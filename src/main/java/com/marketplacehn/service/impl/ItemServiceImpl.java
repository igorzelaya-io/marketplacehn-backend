package com.marketplacehn.service.impl;

import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service Implementation for business logic regarding Item entity.
 * @author Igor A. Zelaya
 * @verion 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item findItemById(final String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Item saveItem(Item item) {
        Item.prepareToPersist(item);
        //if(!item.getItemPhotos().isEmpty() || item.getItemPhotos() != null)
        return itemRepository.save(item);
    }

    @Override
    public void deleteItemById(final String itemId) {
        Item item = findItemById(itemId);
        item.setItemStatus(ModelStatus.INACTIVE);
        itemRepository.save(item);
    }
}
