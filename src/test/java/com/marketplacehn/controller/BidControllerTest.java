package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.enums.ModelStatus;
import com.marketplacehn.request.BidPostingDto;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ContextConfiguration(classes = BidController.class)
class BidControllerTest extends AbstractTestController{
    @MockBean
    private BidService underTest;
    private Bid bid;
    private Item item;
    private BidPostingDto bidDto;
    private Response<Bid> response;
    private static final String BID_ID = UUID.randomUUID().toString();
    private static final String ITEM_ID = UUID.randomUUID().toString();
    private static final int PAGE_NUM = 0;
    private static final int PAGE_SIZE = 10;
    private static final String[] SORT = new String[]{"bidValue,desc"};
    private static final String PREFIX = "/api/v1";

    @BeforeEach()
    void setUp() {
        item = Item.builder()
                .itemId(ITEM_ID)
                .itemPostDate(LocalDateTime.now())
                .itemStatus(ModelStatus.ACTIVE)
                .build();

        bid = Bid.builder()
                .bidId(BID_ID)
                .item(item)
                .bidDate(LocalDateTime.now())
                .build();
        response = new BaseResponse<>();
        response.setPayload(bid);

        bidDto = new BidPostingDto();
        bidDto.setBidToPost(bid);
    }

    @Test
    void itShouldFindBidById() throws Exception {
        when(underTest.findBidById(BID_ID))
                .thenReturn(bid);
        ResultActions result = doRequestFindBidById();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.bidId").value(BID_ID));
    }

    @Test
    void itShouldSaveBid() throws Exception {
        when(underTest.saveBid(bidDto))
                .thenReturn(bid);
        ResultActions result = doRequestSaveBid();
        result
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldDeleteBidById() throws Exception {
        doNothing().when(underTest)
                .deleteBidById(ITEM_ID, BID_ID);
        ResultActions result = doRequestDeleteBidById();
        result
                .andExpect(status().isOk());
    }

    @Test
    void itShouldFindItemBids() throws Exception {
        Page<Bid> bidsPage = new PageImpl<>(List.of(bid));
        when(underTest.findItemBids(ITEM_ID, PAGE_NUM, PAGE_SIZE, SORT))
                .thenReturn(bidsPage);
        ResultActions result = doRequestFindItemBids();
        result
                .andExpect(status().isOk());
    }

    private ResultActions doRequestFindBidById() throws Exception {
        return  mvc.perform(get(PREFIX + "/bids/{bidId}", BID_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveBid() throws Exception {
        return mvc.perform(post(PREFIX + "/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bidDto)));
    }

    private ResultActions doRequestDeleteBidById() throws Exception {
        return mvc.perform(delete(PREFIX + "/items/{itemId}/bids/{bidId}", ITEM_ID, BID_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestFindItemBids() throws Exception {
        return mvc.perform(get(PREFIX + "/items/{itemId}/bids", ITEM_ID)
                .param("page", String.valueOf(PAGE_NUM))
                .param("size", String.valueOf(PAGE_SIZE))
                .param("sort", SORT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }
}