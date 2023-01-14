package com.marketplacehn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplacehn.entity.Bid;
import lombok.Getter;

@JsonSerialize
@Getter
public class BidPostingDto {

    @JsonProperty
    private Bid bidToPost;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String itemId;

}
