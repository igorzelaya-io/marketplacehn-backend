package com.marketplacehn.entity.dto;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.User;
import com.marketplacehn.entity.enums.ItemSellType;

import java.math.BigDecimal;
import java.util.Set;

public record ItemDto(
        User itemOwner,
        BigDecimal itemStartingPrice,
        Set<Bid> itemBids,
        String itemDeliveryAddress,
        ItemSellType itemSellType,
        Set<String> itemPhotos
) {}
