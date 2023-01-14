package com.marketplacehn.repository;

import com.marketplacehn.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Query("SELECT DISTINCT i FROM Item i WHERE i.itemStatus = 1")
    Page<Item> findByActiveStatus(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.itemId = :itemId" +
            " AND i.itemStatus = 1")
    Optional<Item> findActiveItemById(@Param("itemId") String itemId);
}
