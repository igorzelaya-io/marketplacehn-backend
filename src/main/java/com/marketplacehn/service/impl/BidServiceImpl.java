package com.marketplacehn.service.impl;


import com.marketplacehn.entity.Bid;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.service.BidService;
import com.marketplacehn.utils.SortingUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

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
    private final SortingUtils sortingUtils;

    @Override
    public Bid findBidById(final String bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Bid saveBid(Bid bid) {
        Bid.prepareToPersist(bid);
        return bidRepository.save(bid);
    }

    @Override
    public Page<Bid> findItemBids(final String itemId, final int page, final int size, final String[] sort) {


        return null;
    }


}
