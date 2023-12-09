package com.marketplacehn.repository;

import com.marketplacehn.entity.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, String> {

    Page<Bid> findByItem_ItemId(String itemId, Pageable pageable);

    Page<Bid> findByUserBid_UserId(String userId, Pageable pageable);

    @Query("SELECT i, u FROM User u, Item i WHERE i.itemId = :itemId AND u.userId = :userId " +
            "AND i.itemStatus = 1 AND u.userStatus = 1")
    List<Object[]> findUserAndItemById(String itemId, String userId);

}
