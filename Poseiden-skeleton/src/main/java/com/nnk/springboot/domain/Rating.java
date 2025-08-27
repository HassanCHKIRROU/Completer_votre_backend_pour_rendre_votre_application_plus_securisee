package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entité JPA pour la table rating.
 * Champs alignés avec les formulaires Thymeleaf.
 */
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Moodys Rating est obligatoire")
    @Column(name = "moodys_rating")
    private String moodysRating;

    @NotBlank(message = "SandP Rating est obligatoire")
    @Column(name = "sandp_rating")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating est obligatoire")
    @Column(name = "fitch_rating")
    private String fitchRating;

    @NotNull(message = "Order est obligatoire")
    @Column(name = "order_number")
    private Integer order;  // Correspond au champ "order" dans les templates

    public Rating() {}

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer order) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.order = order;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMoodysRating() { return moodysRating; }
    public void setMoodysRating(String moodysRating) { this.moodysRating = moodysRating; }

    public String getSandPRating() { return sandPRating; }
    public void setSandPRating(String sandPRating) { this.sandPRating = sandPRating; }

    public String getFitchRating() { return fitchRating; }
    public void setFitchRating(String fitchRating) { this.fitchRating = fitchRating; }

    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }
}
