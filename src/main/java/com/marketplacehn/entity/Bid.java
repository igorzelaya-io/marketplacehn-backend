package com.marketplacehn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    public Bid(){
        this.bidId = UUID.randomUUID().toString();
        this.bidDate = LocalDateTime.now();
    }

    @Id
    @Column(name = "bid_id", length = 64)
    private String bidId;

    @Column(name = "bidValue", nullable = false)
    @Setter
    private BigDecimal bidValue;

    @Column(name = "bid_date", nullable = false)
    @Setter
    private LocalDateTime bidDate;

    @Column(name = "updated_at")
    @Setter
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    private User userBid;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
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
