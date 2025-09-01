package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Tests unitaires de la classe CurvePointService.
 * Cette classe teste les fonctionnalités CRUD du service CurvePoint en utilisant Mockito
 * pour simuler le repository et AssertJ pour les assertions.
 */
class CurvePointServiceTest {

    private CurvePointRepository repo;
    private CurvePointService service;

    
    //Configuration initiale avant chaque test
    @BeforeEach
    void setUp() {
        repo = mock(CurvePointRepository.class);
        service = new CurvePointServiceImpl(repo);
    }

    
    /*
     * Teste la méthode findAll() lorsqu'elle retourne une liste de CurvePoint.
     * Vérifie que la taille de la liste et les valeurs des termes sont correctes.
     */
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new CurvePoint(1, new BigDecimal("1.00"), new BigDecimal("10.00")),
                new CurvePoint(1, new BigDecimal("2.00"), new BigDecimal("20.00"))
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(CurvePoint::getTerm)
                .containsExactly(new BigDecimal("1.00"), new BigDecimal("2.00"));
    }

    
    /*
     * Teste la méthode findById() avec un ID existant.
     * Vérifie que l'Optional contient une valeur.
     */
    @Test
    void findById_existing_returnsValue() {
        CurvePoint cp = new CurvePoint(1,new BigDecimal("1.00"), new BigDecimal("10.00"));
        cp.setId(5);
        when(repo.findById(5)).thenReturn(Optional.of(cp));

        assertThat(service.findById(5)).isPresent();
    }

    
    /*
     * Teste la méthode findById() avec un ID inconnu.
     * Vérifie que l'Optional retourné est vide.
     */
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(42)).thenReturn(Optional.empty());
        assertThat(service.findById(42)).isEmpty();
    }

    
    /*
     *  Teste la méthode save() pour vérifier qu'elle persiste correctement l'entité.
     * Vérifie que la méthode save du repository est appelée et que la valeur retournée est correcte.
     */
    @Test
    void save_persistsEntity() {
        CurvePoint cp = new CurvePoint(1, new BigDecimal("3.00"), new BigDecimal("30.00"));
        when(repo.save(any(CurvePoint.class))).thenAnswer(inv -> inv.getArgument(0));

        CurvePoint saved = service.save(cp);

        verify(repo).save(cp);
        assertThat(saved.getValue()).isEqualByComparingTo("30.00");
    }

    
    /*
     * Teste la méthode update() avec un ID existant.
     * Vérifie que les champs term et value sont correctement mis à jour.
     */
    @Test
    void update_existing_updatesFields() {
        CurvePoint existing = new CurvePoint(1, new BigDecimal("1.00"), new BigDecimal("10.00"));
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(CurvePoint.class))).thenAnswer(inv -> inv.getArgument(0));

        CurvePoint changes = new CurvePoint(1, new BigDecimal("9.00"), new BigDecimal("99.00"));
        CurvePoint updated = service.update(1, changes);

        assertThat(updated.getTerm()).isEqualByComparingTo("9.00");
        assertThat(updated.getValue()).isEqualByComparingTo("99.00");
        verify(repo).save(existing);
    }

    
    /*
     * Teste la méthode update() avec un ID inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que save() n'est jamais appelé.
     */
    @Test
    void update_unknown_throws() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(999, new CurvePoint()))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).save(any());
    }

    
    /*
     * Teste la méthode deleteById() avec un ID existant.
     * Vérifie que deleteById() est appelé sur le repository.
     */
    @Test
    void delete_existing_ok() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteById(1);
        verify(repo).deleteById(1);
    }

    
    /*
     * Teste la méthode deleteById() avec un ID inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que deleteById() n'est jamais appelé.
     */
    @Test
    void delete_unknown_throws() {
        when(repo.existsById(2)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteById(2))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).deleteById(anyInt());
    }
}
