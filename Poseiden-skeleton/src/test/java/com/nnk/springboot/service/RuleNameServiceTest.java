package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires de la classe RuleNameService.
 * Cette classe teste les fonctionnalités CRUD du service RuleName en utilisant Mockito
 * pour simuler le repository et AssertJ pour les assertions.
 */
class RuleNameServiceTest {

    private RuleNameRepository repo;
    private RuleNameService service;

    
    //Configuration initiale avant chaque test
    @BeforeEach
    void setUp() {
        repo = mock(RuleNameRepository.class);
        service = new RuleNameServiceImpl(repo);
    }

    
    /*
     * Teste la méthode findAll() lorsqu'elle retourne une liste de RuleName.
     * Vérifie que la taille de la liste et les valeurs des noms sont correctes.
     */
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new RuleName("n1", "d1", "j1", "t1", "s1", "sp1"),
                new RuleName("n2", "d2", "j2", "t2", "s2", "sp2")
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(RuleName::getName)
                .containsExactly("n1", "n2");
    }

    
   /*
    * Teste la méthode findById() avec un ID existant.
     * Vérifie que l'Optional contient une valeur pour un RuleName existant. 
    */
    @Test
    void findById_existing_returnsValue() {
        RuleName r = new RuleName("n", "d", "j", "t", "s", "sp");
        r.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(r));

        assertThat(service.findById(1)).isPresent();
    }

    
    /*
     * Teste la méthode findById() avec un ID inconnu.
     * Vérifie que l'Optional retourné est vide pour un ID inexistant
     */
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(9)).thenReturn(Optional.empty());
        assertThat(service.findById(9)).isEmpty();
    }

    
    /*
     * Teste la méthode save() pour vérifier qu'elle persiste correctement l'entité.
     * Vérifie que la méthode save du repository est appelée et que les attributs
     * de l'objet retourné sont corrects (particulièrement le champ sqlPart).
     */
    @Test
    void save_persistsEntity() {
        RuleName r = new RuleName("n", "d", "j", "t", "s", "sp");
        when(repo.save(any(RuleName.class))).thenAnswer(inv -> inv.getArgument(0));

        RuleName saved = service.save(r);
        verify(repo).save(r);
        assertThat(saved.getSqlPart()).isEqualTo("sp");
    }

    
    /*
     * Teste la méthode update() avec un ID existant.
     * Vérifie que tous les champs (name, description, json, template, sql, sqlPart) sont correctement mis à jour. 
     */
    @Test
    void update_existing_updatesFields() {
        RuleName existing = new RuleName("n0", "d0", "j0", "t0", "s0", "sp0");
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(RuleName.class))).thenAnswer(inv -> inv.getArgument(0));

        RuleName changes = new RuleName("n1", "d1", "j1", "t1", "s1", "sp1");
        RuleName updated = service.update(1, changes);

        assertThat(updated.getName()).isEqualTo("n1");
        assertThat(updated.getDescription()).isEqualTo("d1");
        assertThat(updated.getJson()).isEqualTo("j1");
        assertThat(updated.getTemplate()).isEqualTo("t1");
        assertThat(updated.getSql()).isEqualTo("s1");
        assertThat(updated.getSqlPart()).isEqualTo("sp1");
        verify(repo).save(existing);
    }

    
    /*
     * Teste la méthode update() avec un id inconnu.
     * Vérifie qu'une IllegalArgumentException est levée et que save() n'est jamais appelé
     */
    @Test
    void update_unknown_throws() {
        when(repo.findById(404)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(404, new RuleName()))
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
