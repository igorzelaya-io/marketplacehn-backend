package com.marketplacehn.entity.dto;

import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.User;

import java.math.BigDecimal;

public record BidDto(
        User user,
        Item item,
        BigDecimal bidValue
) {}
