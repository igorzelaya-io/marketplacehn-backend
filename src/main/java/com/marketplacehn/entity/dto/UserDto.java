package com.marketplacehn.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplacehn.entity.enums.ModelStatus;

import java.math.BigDecimal;

@JsonSerialize
public record UserDto(
    String userName,
    String userEmail,
    String userPhone,
    String userAddress,
    ModelStatus modelStatus,
    BigDecimal userBalance
){ }
