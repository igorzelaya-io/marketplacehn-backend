package com.marketplacehn.service.impl;


import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.BidValueJson;
import com.marketplacehn.exception.MarketplaceException;
import com.marketplacehn.exception.ResourceNotFoundException;
import com.marketplacehn.exception.enums.MarketplaceError;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.request.BidPostingDto;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ResourceNotFoundException
                        (MarketplaceError.BID_NOT_FOUND.getMessage()));
    }

    @Override
    public Bid saveBid(final BidPostingDto bidDto) {

        Bid bidToPersist = bidDto.getBidToPost();
        Bid.prepareToPersist(bidToPersist);

        //Map List<Object[]> into List<Object>, where Array values are placed into new list.
        List<Object> objectsList = bidRepository
                .findUserAndItemById(bidDto.getItemId(), bidDto.getUserId())
                .stream()
                .flatMap(objects -> Arrays.stream(objects))
                .collect(Collectors.toList());

        //Size == 2 means both Item and User entities were found for Bid.
        if(objectsList.size() == 2){
            final Item bidItem = (Item) objectsList.get(0);

            validateItemHighestBid(bidItem, bidToPersist);

            final User bidUser = (User) objectsList.get(1);

            bidToPersist.setItem(bidItem);
            bidToPersist.setUserBid(bidUser);

            return bidRepository.save(bidToPersist);
        }
        throw new ResourceNotFoundException(MarketplaceError.USER_ITEM_NOT_FOUND.getMessage());
    }

    @Override
    public Bid updateBid(final String bidId, final Bid bid) {
        Bid retrievedBid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException
                        (MarketplaceError.BID_NOT_FOUND.getMessage()));

        retrievedBid.setBidValue(bid.getBidValue());
        retrievedBid.setUpdatedAt(LocalDateTime.now());

        Item bidItem = itemRepository.findActiveItemById(bid.getItem().getItemId())
                .orElseThrow(() -> new ResourceNotFoundException
                        (MarketplaceError.ITEM_NOT_FOUND.getMessage()));

        validateItemHighestBid(bidItem, retrievedBid);

        return bidRepository.save(bid);
    }

    @Override
    public void deleteBidById(final String itemId, final String bidId) {

        final Item bidItem = itemRepository.findActiveItemById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException
                        (MarketplaceError.ITEM_NOT_FOUND.getMessage()));

        //If bid is the current highest, query for second highest to take place.
        if( bidItem.getItemCurrentHighestBid().getBidId().equals(bidId) ) {

            List<Sort.Order> orders = List.of(new Sort.Order(Sort.Direction.DESC, "bidValue"));
            Pageable pageable = PageRequest.of(0, 2, Sort.by(orders));
            final int SECOND_HIGHEST_BID_INDEX = 1;

            final Bid itemSecondHighestBid = bidRepository.findByItem_ItemId(itemId, pageable)
                    .toList().get(SECOND_HIGHEST_BID_INDEX);

            bidItem.setItemCurrentHighestBid
                    (new BidValueJson
                            (itemSecondHighestBid.getBidId(), itemSecondHighestBid.getBidValue()));

            itemRepository.save(bidItem);
        }
        bidRepository.deleteById(bidId);
    }

    @Override
    public Page<Bid> findItemBids(final String itemId, final int page,
                                  final int size, final String[] sort) {
        final List<Sort.Order> sortOrder = sortingUtils.getSortingOrder(sort);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        return bidRepository.findByItem_ItemId(itemId, pageable);
    }

    @Override
    public Page<Bid> findUserBids(final String userId, final int page,
                                  final int size, final String[] sort) {
        final List<Sort.Order> sortOrder = sortingUtils.getSortingOrder(sort);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        return bidRepository.findByUserBid_UserId(userId, pageable);
    }

    private void validateItemHighestBid(Item item, final Bid incomingBid) {
        BidValueJson bidData = item.getItemCurrentHighestBid();

        if(incomingBid.getBidValue().subtract(bidData.getBidValue()).intValue() > 0){

            bidData.setBidValue(incomingBid.getBidValue());
            bidData.setBidId(incomingBid.getBidId());

            item.setItemCurrentHighestBid(bidData);
            itemRepository.save(item);
        } else {
            throw new MarketplaceException(MarketplaceError.CURRENT_BID_IS_HIGHER.getMessage());
        }
    }


}
