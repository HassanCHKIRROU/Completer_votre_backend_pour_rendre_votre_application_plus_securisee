package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Classe de test utilitaire pour générer des hashs BCrypt.
 * Cette classe permet de générer des mots de passe chiffrés pour peupler la base de données
 * ou pour tester le mécanisme d'authentification.
 */
public class BcryptGenTest {
    @Test
    void printHash() {
        String raw = "Admin123!"; // nouveau mot de passe
        String hash = new BCryptPasswordEncoder().encode(raw);
        System.out.println("BCrypt = " + hash);
    }
}
