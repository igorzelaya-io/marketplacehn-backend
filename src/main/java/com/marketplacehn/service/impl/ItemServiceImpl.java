package com.marketplacehn.service.impl;

import com.marketplacehn.entity.Item;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;


/**
 * Service Implementation for business logic regarding Item entity.
 * @author Igor A. Zelaya (igorz@marketplacehn.com)
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
        item.setItemPostDate(LocalDateTime.now());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItemById(final String itemId) {
        itemRepository.deleteById(itemId);
    }
}
