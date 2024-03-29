package com.marketplacehn.entity;

import com.marketplacehn.entity.dto.BidValueJson;
import com.marketplacehn.entity.enums.ItemSellType;
import com.marketplacehn.entity.enums.ModelStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Entity used to represent Item table.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@Entity
@Table(name = "item", schema = "marketplace")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "item_id", length = 64)
    private String itemId;

    @Column(name = "item_starting_price", nullable = false)
    private BigDecimal itemStartingPrice;

    @Column(name = "item_current_bid", columnDefinition = "jsonb")
    @Setter
    @Type(JsonType.class)
    //@Convert(converter = BidValueJsonConverter.class)
    private BidValueJson itemCurrentHighestBid;

    @Column(name = "item_post_date", nullable = false)
    private LocalDateTime itemPostDate;

    @Column(name = "item_delivery_address", length = 126)
    private String itemDeliveryAddress;

    @Column(name = "item_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Setter
    private ModelStatus itemStatus;

    @Column(name = "item_sell_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ItemSellType itemSellType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User itemOwner;

    @OneToMany(mappedBy = "item")
    private Set<Bid> itemBids;

    @ElementCollection
    @CollectionTable(name = "item_photos",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "photo_url")
    private Set<String> itemPhotos;

    public Item() {
        this.itemId = UUID.randomUUID().toString();
        itemStatus = ModelStatus.ACTIVE;
    }

    /**
     * Prepare Item's fields to persist.
     * @param item Item
     * @return Item
     */
    public static Item prepareToPersist(Item item){
        return item.toBuilder()
                .itemId(UUID.randomUUID().toString())
                .itemPostDate(LocalDateTime.now())
                .itemStatus(ModelStatus.ACTIVE)
                .itemCurrentHighestBid(new BidValueJson())
                .build();
    }

}
