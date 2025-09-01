package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entité JPA pour la table Users (mapping conforme au script SQL).
 * - username unique
 * - password avec règles de complexité (majuscule, chiffre, symbole, min 8)
 */
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id") // PK conforme au script
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", nullable = false, unique = true, length = 125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
        message = "Password must contain at least one uppercase letter, one digit and one symbol"
    )
    @Column(name = "password", nullable = false, length = 125) // 125 selon le script, 60 suffisent pour BCrypt
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname", nullable = false, length = 125)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role", nullable = false, length = 125)
    private String role;

    public User() {}

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
