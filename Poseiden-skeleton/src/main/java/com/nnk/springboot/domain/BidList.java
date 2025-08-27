package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

/**
 * Entité JPA pour la table bidlist.
 * Champs alignés avec les formulaires: account, type, bidQuantity.
 */
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Account est obligatoire")
    @Column(nullable = false, length = 100)
    private String account;

    @NotBlank(message = "Type est obligatoire")
    @Column(nullable = false, length = 100)
    private String type;

    @NotNull(message = "Bid Quantity est obligatoire")
    @Positive(message = "Bid Quantity doit être > 0")
    @Digits(integer = 12, fraction = 2, message = "Bid Quantity invalide (max 12 chiffres, 2 décimales)")
    @Column(name = "bid_quantity")
    private Double bidQuantity;

    @Column(name = "creation_ts", nullable = false, updatable = false)
    private Instant creationTs;

    public BidList() {}

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    @PrePersist
    public void prePersist() {
        if (creationTs == null) {
            creationTs = Instant.now();
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getBidQuantity() { return bidQuantity; }
    public void setBidQuantity(Double bidQuantity) { this.bidQuantity = bidQuantity; }

    public Instant getCreationTs() { return creationTs; }
    public void setCreationTs(Instant creationTs) { this.creationTs = creationTs; }
}
