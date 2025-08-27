package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;



class CurvePointServiceTest {
	

    private CurvePointRepository repo;
    private CurvePointService service;

    
    @BeforeEach
    void setUp() {
        repo = mock(CurvePointRepository.class);
        service = new CurvePointServiceImpl(repo);
    }

    
    
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new CurvePoint(1.0, 10.0),
                new CurvePoint(2.0, 20.0)
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(CurvePoint::getTerm)
                .containsExactly(1.0, 2.0);
    }

    
    
    @Test
    void findById_existing_returnsValue() {
        CurvePoint cp = new CurvePoint(1.0, 10.0);
        cp.setId(5);
        when(repo.findById(5)).thenReturn(Optional.of(cp));

        assertThat(service.findById(5)).isPresent();
    }

    
    
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(42)).thenReturn(Optional.empty());
        assertThat(service.findById(42)).isEmpty();
    }

    
    
    @Test
    void save_persistsEntity() {
        CurvePoint cp = new CurvePoint(3.0, 30.0);
        when(repo.save(any(CurvePoint.class))).thenAnswer(inv -> inv.getArgument(0));

        CurvePoint saved = service.save(cp);

        verify(repo).save(cp);
        assertThat(saved.getValue()).isEqualTo(30.0);
    }

    
    
    @Test
    void update_existing_updatesFields() {
        CurvePoint existing = new CurvePoint(1.0, 10.0);
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(CurvePoint.class))).thenAnswer(inv -> inv.getArgument(0));

        CurvePoint changes = new CurvePoint(9.0, 99.0);
        CurvePoint updated = service.update(1, changes);

        assertThat(updated.getTerm()).isEqualTo(9.0);
        assertThat(updated.getValue()).isEqualTo(99.0);
        verify(repo).save(existing);
    }

    
    
    @Test
    void update_unknown_throws() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(999, new CurvePoint()))
                .isInstanceOf(NoSuchElementException.class);
        verify(repo, never()).save(any());
    }

    
    
    @Test
    void delete_existing_ok() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteById(1);
        verify(repo).deleteById(1);
    }

  
    
    @Test
    void delete_unknown_throws() {
        when(repo.existsById(2)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteById(2))
                .isInstanceOf(NoSuchElementException.class);
        verify(repo, never()).deleteById(anyInt());
    }
}
