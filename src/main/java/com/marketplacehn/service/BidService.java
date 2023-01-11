package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import org.springframework.data.domain.Page;

public interface BidService {

    Bid findBidById(final String bidId);

    Bid saveBid(String itemId, Bid bid);

    Bid updateBid(String bidId, Bid bid);

    Page<Bid> findItemBids(String itemId, int page, int size, String[] sort);

    Page<Bid> findUserBids(String userId, int page, int size, String[] sort);

}
