package com.marketplacehn.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemSellType {

    AUCTION(0),
    BUY(1);

    private final int sellType;

}
