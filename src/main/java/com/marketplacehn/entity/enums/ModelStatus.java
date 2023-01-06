package com.marketplacehn.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum used to represent different entities' status.
 */
@RequiredArgsConstructor
@Getter
public enum ModelStatus {

    ACTIVE(1),
    INACTIVE(0);

    private final int statusCode;

}
