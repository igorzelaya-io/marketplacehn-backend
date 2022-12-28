package com.marketplacehn.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ModelStatus {

    ACTIVE(1),
    INACTIVE(0);

    private final int statusCode;

}
