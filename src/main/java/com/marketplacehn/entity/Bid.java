package com.marketplacehn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
import java.util.UUID;

/**
 * Entity used to represent Bid table.
 * @author Igor A. Zelaya
 * @verion 1.0.0
 */
@Entity
@Table(name = "bids", schema = "marketplace")
@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Bid {

    @Id
    @Column(name = "bid_id", length = 64)
    @Setter
    private String bidId;

    @Column(name = "bidValue", nullable = false)
    private BigDecimal bidValue;

    @Column(name = "bid_date", nullable = false)
    @Setter
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

    /**
     * Prepare Bid's fields to persist.
     * @return Bid
     */
    public static Bid prepareToPersist(Bid bid){
        return bid.toBuilder()
                .bidId(UUID.randomUUID().toString())
                .bidDate(LocalDateTime.now())
                .build();
    }
}
