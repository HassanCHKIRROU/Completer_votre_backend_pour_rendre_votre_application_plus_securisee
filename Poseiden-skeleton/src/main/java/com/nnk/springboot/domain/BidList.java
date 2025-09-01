package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Entité JPA pour la table BidList.
 * Compatible avec le script SQL et les templates Thymeleaf.
 */
@Entity
@Table(name = "BidList")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId") // PK dans le script SQL
    private Integer id;

    @NotBlank(message = "Account est obligatoire")
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @NotBlank(message = "Type est obligatoire")
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @NotNull(message = "Bid Quantity est obligatoire")
    @Positive(message = "Bid Quantity doit être > 0")
    @Digits(integer = 20, fraction = 2, message = "Bid Quantity invalide (max 20 chiffres, 2 décimales)")
    @Column(name = "bidQuantity", nullable = false, precision = 20, scale = 2) // Aligné avec DECIMAL(20,2)
    private BigDecimal bidQuantity;

    // Constructeur par défaut
    public BidList() {}

    // Constructeur utile
    public BidList(String account, String type, BigDecimal bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getBidQuantity() { return bidQuantity; }
    public void setBidQuantity(BigDecimal bidQuantity) { this.bidQuantity = bidQuantity; }
}
