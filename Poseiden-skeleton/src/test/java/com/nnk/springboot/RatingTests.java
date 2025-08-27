package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RatingTests {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void rating_crud_ok() {
        // Create & Save (constructeur: moodysRating, sandPRating, fitchRating, order)
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating = ratingRepository.save(rating);

        assertThat(rating.getId()).isNotNull();
        assertThat(rating.getOrder()).isEqualTo(10);

        // Update
        rating.setOrder(20);
        rating = ratingRepository.save(rating);
        assertThat(rating.getOrder()).isEqualTo(20);

        // Find all
        List<Rating> listResult = ratingRepository.findAll();
        assertThat(listResult).isNotEmpty();

        // Delete
        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<Rating> afterDelete = ratingRepository.findById(id);
        assertThat(afterDelete).isNotPresent();
    }
}
