package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.sql.Timestamp;

/**
 * Entité JPA pour la table curvepoint.
 * Champs principaux utilisés dans les formulaires : term, value.
 */
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @NotNull(message = "Term est obligatoire")
    @Positive(message = "Term doit être > 0")
    @Column(nullable = false)
    private Double term;

    @NotNull(message = "Value est obligatoire")
    @Column(nullable = false)
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public CurvePoint() {}

    public CurvePoint(Double term, Double value) {
        this.term = term;
        this.value = value;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCurveId() { return curveId; }
    public void setCurveId(Integer curveId) { this.curveId = curveId; }

    public Timestamp getAsOfDate() { return asOfDate; }
    public void setAsOfDate(Timestamp asOfDate) { this.asOfDate = asOfDate; }

    public Double getTerm() { return term; }
    public void setTerm(Double term) { this.term = term; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Timestamp getCreationDate() { return creationDate; }
    public void setCreationDate(Timestamp creationDate) { this.creationDate = creationDate; }
}
