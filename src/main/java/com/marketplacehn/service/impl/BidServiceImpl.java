package com.marketplacehn.service.impl;


import com.marketplacehn.entity.Bid;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.service.BidService;
import com.marketplacehn.utils.SortingUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for business logic regarding Bid entity.
 * @author Igor A. Zelaya (izelaya@marketplacehn.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    @NonNull
    private final BidRepository bidRepository;

    @NonNull
    private final ItemRepository itemRepository;

    @NonNull
    private final SortingUtils sortingUtils;

    @Override
    public Bid findBidById(final String bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Bid saveBid(final String itemId, Bid bid) {
        Bid.prepareToPersist(bid);

        return itemRepository.findById(itemId).map(item -> {
           bid.setItem(item);
           return bidRepository.save(bid);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Bid updateBid(final String bidId, final Bid bid) {
        Bid retrievedBid = bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException());

        retrievedBid.setBidValue(bid.getBidValue());
        return bidRepository.save(bid);
    }

    @Override
    public Page<Bid> findItemBids(final String itemId, final int page,
                                  final int size, final String[] sort) {
        List<Sort.Order> sortOrder = sortingUtils.getSortingOrder(sort);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        List<Bid> itemBids = bidRepository.findByItem_ItemId(itemId, pageable);
        return new PageImpl<>(itemBids);
    }

    @Override
    public Page<Bid> findUserBids(final String userId, final int page,
                                  final int size, final String[] sort) {
        List<Sort.Order> sortOrder = sortingUtils.getSortingOrder(sort);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        List<Bid> userBids = bidRepository.findByUserBid_UserId(userId, pageable);
        return new PageImpl<>(userBids);
    }
}
