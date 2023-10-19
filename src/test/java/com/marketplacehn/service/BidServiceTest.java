package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.service.impl.BidServiceImpl;
import com.marketplacehn.utils.SortingUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {
    @InjectMocks
    private BidServiceImpl underTest;
    @Mock
    private BidRepository bidRepository;
    @Mock
    private SortingUtils sortingUtils;

    @Test
    void findItemBids() {
        //given
        Bid itemBid = new Bid();
        String bidId = itemBid.getBidId();
        int page = 0;
        int size = 10;
        String[] sort = {"bidValue,desc"};

        //when
        List<Sort.Order> sortingOrder = sortingUtils.getSortingOrder(sort);
        when(sortingUtils.getSortingOrder(sort)).thenReturn(sortingOrder);

        List<Bid> mockBids = createMockBids();
        when(bidRepository.findByItem_ItemId(
                eq(bidId),
                eq(PageRequest.of(page, size, Sort.by(sortingOrder)))
        )).thenReturn(mockBids);

        Page<Bid> itemBids = underTest.findItemBids(bidId, page, size, sort);

        //then
        assertEquals(mockBids.size(), itemBids.getTotalElements());
    }

    private List<Bid> createMockBids() {
        List<Bid> mockBids = new ArrayList<>();
        Bid bid1 = mock(Bid.class);
        Bid bid2 = mock(Bid.class);
        Bid bid3 = mock(Bid.class);

        mockBids.add(bid1);
        mockBids.add(bid2);
        mockBids.add(bid3);

        return mockBids;
    }
}
