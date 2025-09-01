package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entité JPA pour la table CurvePoint (mapping conforme au script SQL).
 * Champs principaux utilisés dans les formulaires : term, value.
 */
@Entity
@Table(name = "CurvePoint")
public class CurvePoint {

	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    
    @Column(name = "CurveId")
    private Integer curveId;

    
    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    
    @NotNull(message = "Term est obligatoire")
    @Positive(message = "Term doit être > 0")
    @Digits(integer = 20, fraction = 2, message = "Term invalide (max 20 chiffres, 2 décimales)")
    @Column(name = "term", nullable = false, precision = 20, scale = 2)
    private BigDecimal term;

    
    @NotNull(message = "Value est obligatoire")
    @Digits(integer = 20, fraction = 2, message = "Value invalide (max 20 chiffres, 2 décimales)")
    @Column(name = "value", nullable = false, precision = 20, scale = 2)
    private BigDecimal value;

    
    @Column(name = "creationDate")
    private Timestamp creationDate;

    
    
    //Constructeur par defaut
    public CurvePoint() {}
 
    //Constructeur avec parametres
    public CurvePoint(Integer curveId, BigDecimal term, BigDecimal value) {
        this.curveId = curveId;
    	this.term = term;
        this.value = value;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    
    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCurveId() { return curveId; }
    public void setCurveId(Integer curveId) { this.curveId = curveId; }

    public Timestamp getAsOfDate() { return asOfDate; }
    public void setAsOfDate(Timestamp asOfDate) { this.asOfDate = asOfDate; }

    public BigDecimal getTerm() { return term; }
    public void setTerm(BigDecimal term) { this.term = term; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public Timestamp getCreationDate() { return creationDate; }
    public void setCreationDate(Timestamp creationDate) { this.creationDate = creationDate; }
}
