package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Digits;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * Entité JPA pour la table Trade (mapping conforme au script SQL).
 * Les formulaires Thymeleaf utilisent surtout account, type et buyQuantity.
 */
@Entity
@Table(name = "Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId") // PK exacte selon le script
    private Integer id;

    @NotBlank(message = "Account est obligatoire")
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @NotBlank(message = "Type est obligatoire")
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @NotNull(message = "Buy Quantity est obligatoire")
    @Positive(message = "Buy Quantity doit être > 0")
    @Digits(integer = 20, fraction = 2, message = "Buy Quantity invalide (max 20 chiffres, 2 décimales)")
    @Column(name = "buyQuantity", precision = 20, scale = 2)
    private BigDecimal buyQuantity;

    @Column(name = "sellQuantity", precision = 20, scale = 2)
    private BigDecimal sellQuantity;

    @Column(name = "buyPrice", precision = 20, scale = 2)
    private BigDecimal buyPrice;

    @Column(name = "sellPrice", precision = 20, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "tradeDate")
    private Timestamp tradeDate;

    @Column(name = "security")
    private String security;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "trader")
    private String trader;

    @Column(name = "book")
    private String book;

    @Column(name = "creationName")
    private String creationName;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @Column(name = "revisionName")
    private String revisionName;

    @Column(name = "revisionDate")
    private Timestamp revisionDate;

    @Column(name = "dealName")
    private String dealName;

    @Column(name = "dealType")
    private String dealType;

    @Column(name = "sourceListId")
    private String sourceListId;

    @Column(name = "side")
    private String side;

    public Trade() {}

    public Trade(String account, String type, BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    // Alias pour compatibilité avec les templates (trade.tradeId)
    public Integer getTradeId() { return id; }
    public void setTradeId(Integer tradeId) { this.id = tradeId; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getBuyQuantity() { return buyQuantity; }
    public void setBuyQuantity(BigDecimal buyQuantity) { this.buyQuantity = buyQuantity; }

    public BigDecimal getSellQuantity() { return sellQuantity; }
    public void setSellQuantity(BigDecimal sellQuantity) { this.sellQuantity = sellQuantity; }

    public BigDecimal getBuyPrice() { return buyPrice; }
    public void setBuyPrice(BigDecimal buyPrice) { this.buyPrice = buyPrice; }

    public BigDecimal getSellPrice() { return sellPrice; }
    public void setSellPrice(BigDecimal sellPrice) { this.sellPrice = sellPrice; }

    public String getBenchmark() { return benchmark; }
    public void setBenchmark(String benchmark) { this.benchmark = benchmark; }

    public Timestamp getTradeDate() { return tradeDate; }
    public void setTradeDate(Timestamp tradeDate) { this.tradeDate = tradeDate; }

    public String getSecurity() { return security; }
    public void setSecurity(String security) { this.security = security; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTrader() { return trader; }
    public void setTrader(String trader) { this.trader = trader; }

    public String getBook() { return book; }
    public void setBook(String book) { this.book = book; }

    public String getCreationName() { return creationName; }
    public void setCreationName(String creationName) { this.creationName = creationName; }

    public Timestamp getCreationDate() { return creationDate; }
    public void setCreationDate(Timestamp creationDate) { this.creationDate = creationDate; }

    public String getRevisionName() { return revisionName; }
    public void setRevisionName(String revisionName) { this.revisionName = revisionName; }

    public Timestamp getRevisionDate() { return revisionDate; }
    public void setRevisionDate(Timestamp revisionDate) { this.revisionDate = revisionDate; }

    public String getDealName() { return dealName; }
    public void setDealName(String dealName) { this.dealName = dealName; }

    public String getDealType() { return dealType; }
    public void setDealType(String dealType) { this.dealType = dealType; }

    public String getSourceListId() { return sourceListId; }
    public void setSourceListId(String sourceListId) { this.sourceListId = sourceListId; }

    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }
}
