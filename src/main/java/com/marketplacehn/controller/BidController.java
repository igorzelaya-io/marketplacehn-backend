package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping("/{bidId}")
    public ResponseEntity<Bid> findBidById(@PathVariable("bidId") final String bidId) {
        Bid bid = bidService.findBidById(bidId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bid> saveBid(@RequestBody(required = true) Bid bid) {
        bidService.saveBid(bid);
        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }

}
