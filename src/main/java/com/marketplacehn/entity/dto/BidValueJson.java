package com.marketplacehn.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonSerialize
@AllArgsConstructor
@NoArgsConstructor
public class BidValueJson {

    @JsonProperty
    private String bidId;

    @JsonProperty
    private BigDecimal bidValue;

}
