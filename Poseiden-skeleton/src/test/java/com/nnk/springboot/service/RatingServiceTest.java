package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class RatingServiceTest {

    private RatingRepository repo;
    private RatingService service;

    
    @BeforeEach
    void setUp() {
        repo = mock(RatingRepository.class);
        service = new RatingServiceImpl(repo);
    }

    
    
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new Rating("M1", "S1", "F1", 1),
                new Rating("M2", "S2", "F2", 2)
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(Rating::getOrder)
                .containsExactly(1, 2);
    }

    
    
    @Test
    void findById_existing_returnsValue() {
        Rating r = new Rating("M", "S", "F", 10);
        r.setId(7);
        when(repo.findById(7)).thenReturn(Optional.of(r));

        assertThat(service.findById(7)).isPresent();
    }

    
    
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(77)).thenReturn(Optional.empty());
        assertThat(service.findById(77)).isEmpty();
    }

  
    
    @Test
    void save_persistsEntity() {
        Rating r = new Rating("M", "S", "F", 10);
        when(repo.save(any(Rating.class))).thenAnswer(inv -> inv.getArgument(0));

        Rating saved = service.save(r);

        verify(repo).save(r);
        assertThat(saved.getMoodysRating()).isEqualTo("M");
    }

    
    
    @Test
    void update_existing_updatesFields() {
        Rating existing = new Rating("M0", "S0", "F0", 0);
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(Rating.class))).thenAnswer(inv -> inv.getArgument(0));

        Rating changes = new Rating("M1", "S1", "F1", 11);
        Rating updated = service.update(1, changes);

        assertThat(updated.getMoodysRating()).isEqualTo("M1");
        assertThat(updated.getSandPRating()).isEqualTo("S1");
        assertThat(updated.getFitchRating()).isEqualTo("F1");
        assertThat(updated.getOrder()).isEqualTo(11);
        verify(repo).save(existing);
    }

    
    
    @Test
    void update_unknown_throws() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(999, new Rating()))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).save(any());
    }

    
    
    @Test
    void delete_existing_ok() {
        when(repo.existsById(2)).thenReturn(true);
        service.deleteById(2);
        verify(repo).deleteById(2);
    }

    
    
    @Test
    void delete_unknown_throws() {
        when(repo.existsById(3)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).deleteById(anyInt());
    }
}
