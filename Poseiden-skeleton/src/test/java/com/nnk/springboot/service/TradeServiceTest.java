package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Tests unitaires de la classe TradeService.
 * Cette classe teste les fonctionnalités CRUD du service Trade en utilisant Mockito
 * pour simuler le repository et AssertJ pour les assertions.
 */
class TradeServiceTest {

    private TradeRepository repo;
    private TradeService service;

    
    //Configuration initiale avant chaque test
    @BeforeEach
    void setUp() {
        repo = mock(TradeRepository.class);
        service = new TradeServiceImpl(repo);
    }

    
    /*
     * Teste la méthode findAll() lorsqu'elle retourne une liste de RuleName.
     * Vérifie que la taille de la liste et les valeurs des noms sont correctes.
     */
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new Trade("acc1", "t1", new BigDecimal("10.00")),
                new Trade("acc2", "t2", new BigDecimal("20.00"))
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(Trade::getAccount)
                .containsExactly("acc1", "acc2");
    }

    
    /*
     * Teste la méthode findById() avec un ID existant.
      * Vérifie que l'Optional contient une valeur pour un RuleName existant. 
     */
    @Test
    void findById_existing_returnsValue() {
        Trade t = new Trade("acc", "type", new BigDecimal("10.00"));
        t.setId(7);
        when(repo.findById(7)).thenReturn(Optional.of(t));

        assertThat(service.findById(7)).isPresent();
    }

    
    /*
     * Teste la méthode findById() avec un ID inconnu.
     * Vérifie que l'Optional retourné est vide pour un ID inexistant
     */
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(77)).thenReturn(Optional.empty());
        assertThat(service.findById(77)).isEmpty();
    }

    
    /*
     * Teste la méthode save() pour vérifier qu'elle persiste correctement l'entité.
     * Vérifie que la méthode save du repository est appelée et que les attributs 
     * de l'objet retourné sont corrects (particulièrement le champ sqlPart)
     */
    @Test
    void save_persistsEntity() {
        Trade t = new Trade("acc", "type", new BigDecimal("15.00"));
        when(repo.save(any(Trade.class))).thenAnswer(inv -> inv.getArgument(0));

        Trade saved = service.save(t);
        verify(repo).save(t);
        assertThat(saved.getBuyQuantity()).isEqualByComparingTo("15.00");
    }

    
    /*
     * Teste la méthode update() avec un ID existant.
     * Vérifie que tous les champs (name, description, json, template, sql, sqlPart) sont correctement mis à jour. 
     */
    @Test
    void update_existing_updatesFields() {
        Trade existing = new Trade("oldAcc", "oldType", new BigDecimal("1.00"));
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(Trade.class))).thenAnswer(inv -> inv.getArgument(0));

        Trade changes = new Trade("newAcc", "newType", new BigDecimal("99.00"));
        Trade updated = service.update(1, changes);

        assertThat(updated.getAccount()).isEqualTo("newAcc");
        assertThat(updated.getType()).isEqualTo("newType");
        assertThat(updated.getBuyQuantity()).isEqualByComparingTo("99.00");
        verify(repo).save(existing);
    }

    
    /*
     * Teste la méthode update() avec un id inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que save() n'est jamais appelé
     */
    @Test
    void update_unknown_throws() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(999, new Trade()))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).save(any());
    }

    
    /*
     * Teste la méthode deleteById() avec un ID existant.
     * Vérifie que deleteById() est appelé sur le repository avec le bon ID
     */
    @Test
    void delete_existing_ok() {
        when(repo.existsById(2)).thenReturn(true);
        service.deleteById(2);
        verify(repo).deleteById(2);
    }

    
    /*
     * Teste la méthode deleteById() avec un ID inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que deleteById() n'est jamais appelé
     */
    @Test
    void delete_unknown_throws() {
        when(repo.existsById(3)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).deleteById(anyInt());
    }
}
