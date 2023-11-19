package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.request.BidPostingDto;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.PaginatedBaseResponse;
import com.marketplacehn.response.PaginatedResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.BidService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/bids/{bidId}")
    public ResponseEntity<? extends Response<Bid>> findBidById(@PathVariable("bidId") final String bidId) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid bid = bidService.findBidById(bidId);
        return response.buildResponseEntity(HttpStatus.OK, "Bid was found.", bid);
    }

    @PostMapping("/bids")
    public ResponseEntity<? extends Response<Bid>> postBid(@RequestBody BidPostingDto bidPostingDto) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid createdBid = bidService.saveBid(bidPostingDto);
        return response.buildResponseEntity(HttpStatus.CREATED, "Bid posted successfully.", createdBid);
    }

    @PutMapping("/bids/{bidId}")
    public ResponseEntity<? extends Response<Bid>> updateBid(@PathVariable final String bidId,
                                                             @RequestBody Bid bid) {
        BaseResponse<Bid> response = new BaseResponse<>();
        final Bid updatedBid = bidService.updateBid(bidId, bid);
        return response.buildResponseEntity(HttpStatus.OK, "Updated Bid", updatedBid);
    }

    @DeleteMapping("/items/{itemId}/bids/{bidId}")
    public ResponseEntity<? extends Response<String>> deleteBidById(@PathVariable final String itemId,
                                                                    @PathVariable final String bidId) {
        BaseResponse<String> response = new BaseResponse<>();
        bidService.deleteBidById(itemId, bidId);
        return response.buildResponseEntity(HttpStatus.OK, "Bid was deleted successfully.", bidId);
    }

    @GetMapping("/items/{itemId}/bids")
    public ResponseEntity<? extends PaginatedResponse<Bid>> getItemsBids(@PathVariable String itemId,
                                                                               @RequestParam(required = false, defaultValue = "0") int page,
                                                                               @RequestParam(required = false, defaultValue = "10") int size,
                                                                               @RequestParam(required = false, defaultValue = "bidValue,desc") String[] sort){
        Page<Bid> bidsPage = bidService
            .findItemBids(itemId, page, size, sort);

        PaginatedResponse<Bid> response = new PaginatedBaseResponse<>();

        return response.buildPaginatedResponseEntity(HttpStatus.OK, "Item bids retrieved successfully", bidsPage.getContent(),
                bidsPage.getNumber(), bidsPage.getSize(), bidsPage.getTotalElements(), bidsPage.getTotalPages());

    }

    @GetMapping("/users/{userId}/bids")
    public ResponseEntity<? extends Response<List<Bid>>> getUserBids(@PathVariable final String userId,
                               @RequestParam(required = false, defaultValue = "0") final int page,
                               @RequestParam(required = false, defaultValue = "10") final int size,
                               @RequestParam(required = false, defaultValue = "bidValue,desc") final String[] sort) {
        Page<Bid> bidsPage = bidService
                .findUserBids(userId, page, size, sort);

        PaginatedResponse<Bid> response = new PaginatedBaseResponse<>();

        return response.buildPaginatedResponseEntity(HttpStatus.OK, "Item bids retrieved successfully", bidsPage.getContent(),
                bidsPage.getNumber(), bidsPage.getSize(), bidsPage.getTotalElements(), bidsPage.getTotalPages());
    }


}
