package com.marketplacehn.repository;

import com.marketplacehn.entity.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BidRepository extends JpaRepository<Bid, String> {

    Page<Bid> findByItem_ItemId(String itemId, Pageable pageable);

    @Transactional
    void deleteByItem_ItemId(String itemId);

}
