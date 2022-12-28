package com.marketplacehn.entity;

import com.marketplacehn.entity.enums.ItemSellType;
import com.marketplacehn.entity.enums.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "item", schema = "marketplace")
@Getter
@Builder
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "item_id", length = 64)
    private String itemId;

    @Column(name = "item_starting_price", nullable = false)
    private BigDecimal itemStartingPrice;

    @Column(name = "item_current_bid")
    private BigDecimal itemCurrentBid;

    @Column(name = "item_post_date", nullable = false)
    private LocalDateTime itemPostDate;

    @Column(name = "item_delivery_address", length = 126)
    private String itemDeliveryAddress;

    @Column(name = "item_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ModelStatus itemStatus;

    @Column(name = "item_sell_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ItemSellType itemSellType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User itemOwner;

    @OneToMany(mappedBy = "item",
        cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE }
    )
    private Set<Bid> itemBids;

}
