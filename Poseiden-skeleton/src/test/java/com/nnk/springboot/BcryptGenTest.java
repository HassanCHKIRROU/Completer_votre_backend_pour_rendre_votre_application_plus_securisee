package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptGenTest {
    @Test
    void printHash() {
        String raw = "Admin123!"; // nouveau mot de passe
        String hash = new BCryptPasswordEncoder().encode(raw);
        System.out.println("BCrypt = " + hash);
    }
}
