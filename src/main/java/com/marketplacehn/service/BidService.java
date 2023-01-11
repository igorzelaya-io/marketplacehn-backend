package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import org.springframework.data.domain.Page;

public interface BidService {

    Bid findBidById(final String bidId);

    Bid saveBid(Bid bid);

    Page<Bid> findItemBids(String itemId, int page, int size, String[] sort);

}
