/*package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncodeTest {

    @Test
    void bcrypt_encode_and_match() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Mot de passe conforme aux règles (≥8, 1 maj, 1 chiffre, 1 symbole)
        String raw = "Abcdef1!";

        String hash = encoder.encode(raw);

        // Vérifie que le hash correspond au mot de passe
        assertThat(encoder.matches(raw, hash)).isTrue();

        // Vérifie qu'un mauvais mot de passe ne matche pas
        assertThat(encoder.matches("WrongPass1!", hash)).isFalse();

        // Optionnel: afficher le hash si on veut le voir dans la console
        // System.out.println("Hash = " + hash);
    }
}
*/