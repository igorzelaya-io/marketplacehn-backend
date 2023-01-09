package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.BidService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for Bid entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    @NonNull
    private final BidService bidService;

    @GetMapping("/{bidId}")
    public ResponseEntity<? extends Response<Bid>> findBidById(@PathVariable("bidId") final String bidId) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid bid = bidService.findBidById(bidId);
        return response.buildResponseEntity(HttpStatus.OK, "Bid was found.", bid);
    }

    @PostMapping
    public ResponseEntity<? extends Response<Bid>> saveBid(@RequestBody Bid bid) {
        BaseResponse<Bid> response = new BaseResponse<>();
        bidService.saveBid(bid);
        return response.buildResponseEntity(HttpStatus.CREATED, "Bid was placed.", bid);
    }

}
