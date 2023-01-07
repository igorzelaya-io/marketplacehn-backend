package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;

public interface BidService {

    Bid findBidById(final String bidId);

    Bid saveBid(Bid bid);

}
