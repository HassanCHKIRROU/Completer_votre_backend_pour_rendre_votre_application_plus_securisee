package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entité JPA pour la table rulename.
 * Champs alignés avec les formulaires Thymeleaf.
 */
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name est obligatoire")
    private String name;

    @NotBlank(message = "Description est obligatoire")
    private String description;

    @NotBlank(message = "Json est obligatoire")
    private String json;

    @NotBlank(message = "Template est obligatoire")
    private String template;

    @NotBlank(message = "SQL est obligatoire")
    @Column(name = "sql_str")
    private String sql;

    @NotBlank(message = "SQL Part est obligatoire")
    @Column(name = "sql_part")
    private String sqlPart;

    public RuleName() {}

    public RuleName(String name, String description, String json, String template, String sql, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sql = sql;
        this.sqlPart = sqlPart;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getJson() { return json; }
    public void setJson(String json) { this.json = json; }

    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }

    public String getSql() { return sql; }
    public void setSql(String sql) { this.sql = sql; }

    public String getSqlPart() { return sqlPart; }
    public void setSqlPart(String sqlPart) { this.sqlPart = sqlPart; }
}
