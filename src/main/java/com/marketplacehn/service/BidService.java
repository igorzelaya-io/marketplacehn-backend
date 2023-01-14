package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.request.BidPostingDto;
import org.springframework.data.domain.Page;

public interface BidService {

    Bid findBidById(final String bidId);

    Bid saveBid(BidPostingDto bidDto);

    Bid updateBid(String bidId, Bid bid);

    void deleteBidById(String itemId, String bidId);

    Page<Bid> findItemBids(String itemId, int page, int size, String[] sort);

    Page<Bid> findUserBids(String userId, int page, int size, String[] sort);

}
