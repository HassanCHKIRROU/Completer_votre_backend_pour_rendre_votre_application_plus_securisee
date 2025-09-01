package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires de la classe BidListService.
 * Cette classe teste les fonctionnalités CRUD du service BidList en utilisant Mockito
 * pour simuler le repository et AssertJ pour les assertions.
 */
class BidListServiceTest {

    private BidListRepository repo;
    private BidListService service;

    
    //Configuration initiale avant chaque test
    @BeforeEach
    void setUp() {
        repo = mock(BidListRepository.class);
        service = new BidListServiceImpl(repo);
    }

    
    /*
     * Teste la méthode findAll() lorsqu'elle retourne une liste de BidList.
     * Vérifie que la taille de la liste et les valeurs des comptes sont correctes.
     */
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new BidList("acc1", "type1", new BigDecimal("10.00")),
                new BidList("acc2", "type2", new BigDecimal("20.00"))
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(BidList::getAccount)
                .containsExactly("acc1", "acc2");
    }

    
    /*
     * Teste la méthode findById() avec un ID existant.
     * Vérifie que l'Optional contient la bonne valeur et que le compte est correct.
     */
    @Test
    void findById_existing_returnsOptionalWithValue() {
        BidList b = new BidList("acc", "type", new BigDecimal("5.00"));
        b.setId(42);
        when(repo.findById(42)).thenReturn(Optional.of(b));

        assertThat(service.findById(42))
                .isPresent()
                .get()
                .extracting(BidList::getAccount)
                .isEqualTo("acc");
    }

    
    /*
     * Teste la méthode findById() avec un id inconnu.
     * Vérifie que l'Optional retourné est vide.
     */
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThat(service.findById(99)).isEmpty();
    }

    
    /*
     * Teste la méthode save() pour vérifier qu'elle persiste correctement l'entité.
     * Utilise ArgumentCaptor pour capturer l'objet passé au repository.
     */
    @Test
    void save_persistsEntity() {
        BidList bid = new BidList("acc", "type", new BigDecimal("15.50"));
        when(repo.save(any(BidList.class))).thenAnswer(inv -> inv.getArgument(0));

        BidList saved = service.save(bid);

        ArgumentCaptor<BidList> captor = ArgumentCaptor.forClass(BidList.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getAccount()).isEqualTo("acc");
        assertThat(saved.getBidQuantity()).isEqualByComparingTo("15.50");
    }

    
    /*
     * Teste la méthode update() avec un ID existant.
     * Vérifie que les champs sont correctement mis à jour et que save() est appelé
     */
    @Test
    void update_existing_updatesFields() {
        BidList existing = new BidList("oldAcc", "oldType", new BigDecimal("1.00"));
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(BidList.class))).thenAnswer(inv -> inv.getArgument(0));

        BidList toUpdate = new BidList("newAcc", "newType", new BigDecimal("99.90"));
        BidList updated = service.update(1, toUpdate);

        assertThat(updated.getAccount()).isEqualTo("newAcc");
        assertThat(updated.getBidQuantity()).isEqualByComparingTo("99.90");
        verify(repo).save(existing);
    }

    
    /*
     * Teste la méthode update() avec un ID inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que save() n'est jamais appelé.
     */
    @Test
    void update_unknownId_throws_andDoesNotSave() {
        when(repo.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999, new BidList()))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo, never()).save(any());
    }

    
    /*
     * Teste la méthode deleteById() avec un ID existant.
     * Vérifie que deleteById() est appelé sur le repository.
     */
    @Test
    void deleteById_existing_deletes() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteById(1);
        verify(repo).deleteById(1);
    }

    
    /*
     * Teste la méthode deleteById() avec un ID inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que deleteById() n'est jamais appelé
     */
    @Test
    void deleteById_unknown_throws() {
        when(repo.existsById(123)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteById(123))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repo, never()).deleteById(anyInt());
    }
}
