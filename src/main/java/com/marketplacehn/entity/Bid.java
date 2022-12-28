package com.marketplacehn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids", schema = "marketplace")
@Getter
@AllArgsConstructor
public class Bid {

    @Id
    @Column(name = "bid_id", length = 64)
    private String bidId;

    @Column(name = "bidValue", nullable = false)
    private BigDecimal bidValue;

    @Column(name = "bid_date", nullable = false)
    private LocalDateTime bidDate;

    @ManyToOne(fetch = FetchType.EAGER,
        cascade = { CascadeType.MERGE, CascadeType.REFRESH }
    )
    @JoinColumn(name = "user_id")
    private User userBid;

    @ManyToOne(fetch = FetchType.EAGER,
        cascade = { CascadeType.REFRESH, CascadeType.MERGE }
    )
    @JoinColumn(name = "item_id")
    private Item item;

}
