package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.BidValueJson;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.request.BidPostingDto;
import com.marketplacehn.service.impl.BidServiceImpl;
import com.marketplacehn.utils.SortingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {
    private BidService underTest;
    @Mock
    private BidRepository bidRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private SortingUtils sortingUtils;

    @BeforeEach
    void setUp() {
        underTest = new BidServiceImpl(
                bidRepository,
                itemRepository,
                sortingUtils
        );
    }

    @Test
    @Disabled
    void itShouldSaveBid() {
        //given
        Bid bid = new Bid();
        Item item = new Item();
        User user = new User();

        String userId = user.getUserId();
        String itemId = item.getItemId();

        item.setItemCurrentHighestBid(new BidValueJson("previousBid123", new BigDecimal("120.0")));
        bid.setBidValue(new BigDecimal("150.0"));

        List<Object> objectList = new ArrayList<>();
        objectList.add(item);
        objectList.add(user);

        BidPostingDto bidPostingDto = new BidPostingDto();
        bidPostingDto.setBidToPost(bid);
        bidPostingDto.setUserId(userId);
        bidPostingDto.setItemId(itemId);

        //when
//        when(bidRepository
//                .findUserAndItemById(itemId, userId)).thenReturn(objectList);

        Bid expectedBid = underTest.saveBid(bidPostingDto);

        //then
        assertEquals(bid, expectedBid);
        verify(underTest, times(1)).saveBid(bidPostingDto);
    }

    @Test
    @Disabled
    void itShouldUpdateBid() {
        //given
        Bid existingBid = new Bid();
        String existingBidId = existingBid.getBidId();

        Bid updatedBid = new Bid();
        String updatedBidId = updatedBid.getBidId();
        updatedBid.setBidValue(new BigDecimal("150.0"));

        Item updatedBidItem = new Item();
        updatedBidItem.setItemCurrentHighestBid(
                new BidValueJson(updatedBidId, new BigDecimal("120.0"))
        );
        updatedBid.setItem(updatedBidItem);

        //when
        when(bidRepository.findById(existingBidId)).thenReturn(Optional.of(existingBid));
        when(itemRepository.findActiveItemById(updatedBid.getItem().getItemId()))
                .thenReturn(Optional.of(updatedBidItem));
        when(bidRepository.save(any(Bid.class))).thenAnswer(invocation -> {
            return invocation.<Bid>getArgument(0);
        });
        Bid expectedBid = underTest.updateBid(existingBidId, updatedBid);

        //then
        assertEquals(LocalDateTime.now(), existingBid.getUpdatedAt());
        verify(underTest, times(1))
                .updateBid(existingBidId, updatedBid);
    }

    @Test
    void itShouldDeleteBidById() {
        //given
        Item item = new Item();
        Bid highestBid = new Bid();
        String itemId = item.getItemId();
        String bidId = highestBid.getBidId();

        item.setItemCurrentHighestBid(new BidValueJson("bid123", TEN));
        Page<Bid> mockBids = createMockBids();

        //when
        when(itemRepository.findActiveItemById(itemId))
                .thenReturn(Optional.of(item));
        when(bidRepository.findByItem_ItemId(
                eq(itemId),
                any(Pageable.class)
        )).thenReturn(mockBids);
        doNothing().when(bidRepository).deleteById(bidId);

        underTest.deleteBidById(itemId, bidId);

        //then
        //highest bid not removed
        assertEquals("bid123", item.getItemCurrentHighestBid().getBidId());

        item.getItemCurrentHighestBid().setBidId(bidId);
        underTest.deleteBidById(itemId, bidId);

        //highest bid removed
        assertNull(item.getItemCurrentHighestBid().getBidId());
        assertNull(item.getItemCurrentHighestBid().getBidValue());

        verify(bidRepository, times(2)).deleteById(bidId);
    }

    @Test
    void itShouldFindAllItemBids() {
        //given
        Bid itemBid = mock(Bid.class);
        String bidId = itemBid.getBidId();
        int page = 0;
        int size = 10;
        String[] sort = {"bidValue,desc"};

        List<Sort.Order> sortingOrder = new ArrayList<>();
        sortingOrder.add(new Sort.Order(Sort.Direction.DESC, "bidValue"));

        Page<Bid> mockBids = createMockBids();

        //when
        when(sortingUtils.getSortingOrder(sort)).thenReturn(sortingOrder);

        when(bidRepository.findByItem_ItemId(
                eq(bidId),
                eq(PageRequest.of(page, size, Sort.by(sortingOrder)))
        )).thenReturn(mockBids);

        Page<Bid> itemBids = underTest.findItemBids(bidId, page, size, sort);

        //then
        assertEquals(mockBids.getTotalElements(), itemBids.getTotalElements());
    }

    @Test
    void itShouldFindAllUserBids() {
        //given
        User user = mock(User.class);
        String userId = user.getUserId();
        int page = 0;
        int size = 10;
        String[] sort = {"bidValue,desc"};

        List<Sort.Order> sortingOrder = new ArrayList<>();
        sortingOrder.add(new Sort.Order(Sort.Direction.DESC, "bidValue"));

        Page<Bid> mockBids = createMockBids();

        //when
        when(sortingUtils.getSortingOrder(sort)).thenReturn(sortingOrder);

        when(bidRepository.findByUserBid_UserId(
                eq(userId),
                eq(PageRequest.of(page, size, Sort.by(sortingOrder)))
        )).thenReturn(mockBids);

        Page<Bid> userBids = underTest.findUserBids(userId, page, size, sort);

        //then
        assertEquals(mockBids.getTotalElements(), userBids.getTotalElements());
    }

    private Page<Bid> createMockBids() {
        List<Bid> mockBids = new ArrayList<>();
        mockBids.add(mock(Bid.class));
        mockBids.add(mock(Bid.class));
        mockBids.add(mock(Bid.class));
        return new PageImpl<>(mockBids);
    }

}
