package com.marketplacehn.service.impl;


import com.marketplacehn.entity.Bid;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

/**
 * Service implementation for business logic regarding Bid entity.
 * @author Igor A. Zelaya (izelaya@marketplacehn.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    @Override
    public Bid findBidById(final String bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Bid saveBid(Bid bid) {
        bid.setBidDate(LocalDateTime.now());
        return bidRepository.save(bid);
    }
}
