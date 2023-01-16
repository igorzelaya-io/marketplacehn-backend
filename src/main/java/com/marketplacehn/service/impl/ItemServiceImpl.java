package com.marketplacehn.service.impl;

import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.service.ItemService;
import com.marketplacehn.utils.SortingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * Service Implementation for business logic regarding Item entity.
 * @author Igor A. Zelaya
 * @verion 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final SortingUtils sortingUtils;

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

    @Override
    public Page<Item> findAllItemsByUserId(String userId, int page, int size, String[] sort) {
        List<Sort.Order> orders = sortingUtils.getSortingOrder(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<Item> items = itemRepository.findByItemOwner_UserId(userId, pageable);
        return new PageImpl<>(items);
    }

    //Update Item Purchased Status

}
