package com.marketplacehn.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum used to represent an Item's sell type.
 */
@Getter
@RequiredArgsConstructor
public enum ItemSellType {

    AUCTION(0),
    BUY(1);

    private final int sellType;

}
