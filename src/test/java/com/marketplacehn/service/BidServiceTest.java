package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.User;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.repository.ItemRepository;
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
    private ItemRepository itemRepository;
    @Mock
    private SortingUtils sortingUtils;

    @Test
    void shouldFindAllItemBids() {
        //given
        Bid itemBid = mock(Bid.class);
        String bidId = itemBid.getBidId();
        int page = 0;
        int size = 10;
        String[] sort = {"bidValue,desc"};

        List<Sort.Order> sortingOrder = new ArrayList<>();
        sortingOrder.add(new Sort.Order(Sort.Direction.DESC, "bidValue"));

        List<Bid> mockBids = createMockBids();

        //when
        when(sortingUtils.getSortingOrder(sort)).thenReturn(sortingOrder);

        when(bidRepository.findByItem_ItemId(
                eq(bidId),
                eq(PageRequest.of(page, size, Sort.by(sortingOrder)))
        )).thenReturn(mockBids);

        Page<Bid> itemBids = underTest.findItemBids(bidId, page, size, sort);

        //then
        assertEquals(mockBids.size(), itemBids.getTotalElements());
    }

    @Test
    void shouldFindAllUserBids() {
        //given
        User user = mock(User.class);
        String userId = user.getUserId();
        int page = 0;
        int size = 10;
        String[] sort = {"bidValue,desc"};

        List<Sort.Order> sortingOrder = new ArrayList<>();
        sortingOrder.add(new Sort.Order(Sort.Direction.DESC, "bidValue"));

        List<Bid> mockBids = createMockBids();

        //when
        when(sortingUtils.getSortingOrder(sort)).thenReturn(sortingOrder);

        when(bidRepository.findByUserBid_UserId(
                eq(userId),
                eq(PageRequest.of(page, size, Sort.by(sortingOrder)))
        )).thenReturn(mockBids);

        Page<Bid> userBids = underTest.findUserBids(userId, page, size, sort);

        //then
        assertEquals(mockBids.size(), userBids.getTotalElements());
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
