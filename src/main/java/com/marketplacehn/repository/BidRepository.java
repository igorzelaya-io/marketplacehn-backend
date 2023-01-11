package com.marketplacehn.repository;

import com.marketplacehn.entity.Bid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, String> {

    List<Bid> findByItem_ItemId(String itemId, Pageable pageable);

    List<Bid> findByUserBid_UserId(String userId, Pageable pageable);

    @Transactional
    void deleteByItem_ItemId(String itemId);

}
