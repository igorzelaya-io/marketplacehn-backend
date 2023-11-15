package com.marketplacehn.service;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.entity.Item;
import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.BidValueJson;
import com.marketplacehn.exception.MarketplaceException;
import com.marketplacehn.exception.ResourceNotFoundException;
import com.marketplacehn.repository.BidRepository;
import com.marketplacehn.repository.ItemRepository;
import com.marketplacehn.request.BidPostingDto;
import com.marketplacehn.service.impl.BidServiceImpl;
import com.marketplacehn.utils.SortingUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    private static Bid bid;
    private static Bid secondHighestBid;
    private static Item item;
    private static User user;
    private static final BidPostingDto bidDto = new BidPostingDto();
    private static final String BID_ID = UUID.randomUUID().toString();
    private static final String ITEM_ID = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final int PAGE_NUM = 0;
    private static final int PAGE_SIZE = 10;
    private static final String[] SORT = new String[]{"bidValue,desc"};
    private static final List<Object[]> OBJECTS = new ArrayList<>();

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(USER_ID)
                .build();
        bid = Bid.builder()
                .bidId(BID_ID)
                .bidValue(new BigDecimal("150"))
                .build();
        item = Item.builder()
                .itemId(ITEM_ID)
                .build();
        bidDto.setBidToPost(bid);
        bidDto.setUserId(USER_ID);
        bidDto.setItemId(ITEM_ID);
        OBJECTS.add(new Object[]{item});
    }

    @Test
    void findBidById_whenBidExists() {
        when(bidRepository.findById(BID_ID))
                .thenReturn(Optional.of(bid));

        Bid expectedBid = underTest.findBidById(BID_ID);

        Assertions.assertEquals(bid, expectedBid);
    }

    @Test
    void findBidById_whenBidDoesNotExists() {
        when(bidRepository.findById(BID_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> underTest.findBidById(BID_ID));
    }

    @Test
    void saveBid_UserAndItemFound_whenCurrentBidIsNotHigher() {
        item.setItemCurrentHighestBid(
                new BidValueJson(
                        BID_ID,
                        new BigDecimal("120")
                ));
        OBJECTS.remove(0);
        OBJECTS.add(new Object[]{item, user});

        when(bidRepository.findUserAndItemById(ITEM_ID, USER_ID))
                .thenReturn(OBJECTS);
        when(bidRepository.save(bid))
                .thenReturn(bid);

        Bid expectedBid = underTest.saveBid(bidDto);

        Assertions.assertEquals(bid, expectedBid);
        Assertions.assertEquals(item, expectedBid.getItem());
        Assertions.assertEquals(user, expectedBid.getUserBid());
    }

    @Test
    void saveBid_UserAndItemFound_whenCurrentBidIsHigher() {
        item.setItemCurrentHighestBid(
                new BidValueJson(
                        BID_ID,
                        new BigDecimal("150")
                ));
        OBJECTS.remove(0);
        OBJECTS.add(new Object[]{item, user});

        when(bidRepository.findUserAndItemById(ITEM_ID, USER_ID))
                .thenReturn(OBJECTS);

        Assertions.assertThrows(
                MarketplaceException.class,
                () -> underTest.saveBid(bidDto));
    }

    @Test
    void saveBid_UserAndItemNotFound() {
        when(bidRepository.findUserAndItemById(ITEM_ID, USER_ID))
                .thenReturn(OBJECTS);

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> underTest.saveBid(bidDto));
    }

    @Test
    void updateBid_whenCurrentBidIsHigher() {
        Bid updatedBid = Bid.builder()
                .bidValue(new BigDecimal("200"))
                .item(item)
                .build();

        item.setItemCurrentHighestBid(
                new BidValueJson(
                        UUID.randomUUID().toString(),
                        new BigDecimal("150")
                ));

        when(bidRepository.findById(BID_ID)).
                thenReturn(Optional.of(bid));
        when(itemRepository.findActiveItemById(ITEM_ID))
                .thenReturn(Optional.of(item));
        when(itemRepository.save(item))
                .thenReturn(item);
        when(bidRepository.save(updatedBid)).thenReturn(updatedBid);

        Bid expectedBid = underTest.updateBid(BID_ID, updatedBid);

        Assertions.assertEquals(
                updatedBid.getBidValue(),
                bid.getBidValue());
    }

    @Test
    void updateBid_whenCurrentBidIsNotHigher() {
        Bid updatedBid = Bid.builder()
                .bidValue(new BigDecimal("200"))
                .item(item)
                .build();

        item.setItemCurrentHighestBid(
                new BidValueJson(
                        UUID.randomUUID().toString(),
                        new BigDecimal("250")
                ));

        when(bidRepository.findById(BID_ID)).
                thenReturn(Optional.of(bid));
        when(itemRepository.findActiveItemById(ITEM_ID))
                .thenReturn(Optional.of(item));

        Assertions.assertThrows(
                MarketplaceException.class,
                () -> underTest.updateBid(BID_ID, updatedBid)
        );
    }

    @Test
    void deleteBidById_currentHighest() {
        item.setItemCurrentHighestBid(
                new BidValueJson(
                        BID_ID,
                        new BigDecimal("150")
                ));
        List<Bid> fakeBids = createFakeBids();

        when(itemRepository.findActiveItemById(ITEM_ID))
                .thenReturn(Optional.of(item));
        when(bidRepository.findByItem_ItemId(eq(ITEM_ID), any()))
                .thenReturn(fakeBids);
        when(itemRepository.save(item))
                .thenReturn(item);

        underTest.deleteBidById(ITEM_ID, BID_ID);

        Assertions.assertEquals(
                secondHighestBid.getBidValue(),
                item.getItemCurrentHighestBid().getBidValue()
                );
    }

    @Test
    void deleteBidById_notCurrentHighest() {
        item.setItemCurrentHighestBid(
                new BidValueJson(
                        UUID.randomUUID().toString(),
                        new BigDecimal("100")
                ));
        when(itemRepository.findActiveItemById(ITEM_ID))
                .thenReturn(Optional.of(item));
        doNothing()
                .when(bidRepository).deleteById(BID_ID);

        underTest.deleteBidById(ITEM_ID, BID_ID);

        verify(bidRepository, times(1))
                .deleteById(BID_ID);
    }

    @Test
    void itShouldFindAllItemBids() {
        List<Bid> fakeBids = createFakeBids();

        when(bidRepository.findByItem_ItemId(eq(ITEM_ID), any()))
                .thenReturn(fakeBids);

        Page<Bid> expectedPage = underTest
                .findItemBids(ITEM_ID, PAGE_NUM, PAGE_SIZE, SORT);

        Assertions.assertEquals(fakeBids.size(), expectedPage.getTotalElements());
    }

    @Test
    void itShouldFindAllUserBids() {
        List<Bid> fakeBids = createFakeBids();

        when(bidRepository.findByItem_ItemId(eq(USER_ID), any()))
                .thenReturn(fakeBids);

        Page<Bid> expectedPage = underTest
                .findItemBids(USER_ID, PAGE_NUM, PAGE_SIZE, SORT);

        Assertions.assertEquals(fakeBids.size(), expectedPage.getTotalElements());
    }

    private List<Bid> createFakeBids() {
        List<Bid> fakeBids = new ArrayList<>();
        fakeBids.add(bid);
        fakeBids.add(secondHighestBid);
        return fakeBids;
    }

}
