package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.PageableResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.BidService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Rest Controller for Bid entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BidController {

    @NonNull
    private final BidService bidService;

    @GetMapping("/items/{itemId}/bids")
    public ResponseEntity<? extends Response<Bid>> getItemsBids(@PathVariable String itemId,
                                      @RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "10") int size,
                                      @RequestParam(required = false, defaultValue = "bidValue,desc") String[] sort){

        PageableResponse<Bid> pageResponse = new PageableResponse<>();
        Page<Bid> bidsPage = bidService
                .findItemBids(itemId, page, size, sort);

        return pageResponse
                .buildResponseEntity
                        (HttpStatus.OK, "Item bids retrieved.", bidsPage.getSize(), bidsPage.getNumberOfElements(),
                                bidsPage.getTotalPages(), bidsPage.getNumber(), bidsPage.getContent());

    }


    @GetMapping("/users/{userId}/bids")
    public ResponseEntity<? extends Response<Bid>> getUserBids(@PathVariable final String userId,
                               @RequestParam(required = false, defaultValue = "0") final int page,
                               @RequestParam(required = false, defaultValue = "10") final int size,
                               @RequestParam(required = false, defaultValue = "bidValue,desc") final String sort[]) {
        PageableResponse<Bid> pageResponse = new PageableResponse<>();
        Page<Bid> bidsPage = bidService
                .findUserBids(userId, page, size, sort);

        return pageResponse
                .buildResponseEntity
                        (HttpStatus.OK, "User bids retrieved.", bidsPage.getSize(), bidsPage.getNumberOfElements(),
                                bidsPage.getTotalPages(), bidsPage.getNumber(), bidsPage.getContent());
    }


    @GetMapping("bids/{bidId}")
    public ResponseEntity<? extends Response<Bid>> findBidById(@PathVariable("bidId") final String bidId) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid bid = bidService.findBidById(bidId);
        return response.buildResponseEntity(HttpStatus.OK, "Bid was found.", bid);
    }

    @PostMapping("/bids")
    public ResponseEntity<? extends Response<Bid>> postBid(@PathVariable final String itemId,
                                                                 @RequestBody Bid bid) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid createdBid = bidService.saveBid(itemId, bid);
        return response.buildResponseEntity(HttpStatus.OK, "Bid created successfully.", createdBid);
    }

    @PutMapping("/bids/{bidId}")
    public ResponseEntity<? extends Response<Bid>> updateBid(@PathVariable final String bidId,
                                                             @RequestBody Bid bid) {
        BaseResponse<Bid> response = new BaseResponse<>();



    }

}
