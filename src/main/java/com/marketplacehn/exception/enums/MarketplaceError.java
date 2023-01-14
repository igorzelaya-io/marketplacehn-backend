package com.marketplacehn.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MarketplaceError {

    //ITEM
    ITEM_NOT_FOUND("Item was not found."),

    //BIDS
    BID_NOT_FOUND("Bid was not found"),
    CURRENT_BID_IS_HIGHER("Bid's value is lower than highest placed bid."),

    //USER ITEM QUERY
    USER_ITEM_NOT_FOUND("User and/or Item was not found.");


    private final String message;
}
