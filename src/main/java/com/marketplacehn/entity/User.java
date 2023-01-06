package com.marketplacehn.entity;


import com.marketplacehn.entity.enums.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;


/**
 * Entity used to represent User table.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@Entity
@Table(name = "users", schema = "marketplace")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", length = 64)
    private String userId;

    @Column(name = "user_name", nullable = false, length = 64)
    private String userName;

    @Column(name = "user_email", nullable = false, length = 64)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 32)
    private String userPassword;

    @Column(name = "user_phone", nullable = false, length = 16)
    private String userPhone;

    @Column(name = "user_address", length = 256)
    private String user_address;

    @Column(name = "user_photo_url", length = 256)
    private String user_photo_url;

    @Column(name = "user_balance", nullable = false)
    private BigDecimal userBalance;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ModelStatus userStatus;

    @OneToMany(mappedBy = "itemOwner",
            cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Item> items;

    @OneToMany(mappedBy = "userBid",
            cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST}
    )
    private Set<Bid> userBids;

}
