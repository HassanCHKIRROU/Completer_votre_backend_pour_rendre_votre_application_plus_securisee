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

class TradeServiceTest {

    private TradeRepository repo;
    private TradeService service;

    @BeforeEach
    void setUp() {
        repo = mock(TradeRepository.class);
        service = new TradeServiceImpl(repo);
    }

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

    @Test
    void findById_existing_returnsValue() {
        Trade t = new Trade("acc", "type", new BigDecimal("10.00"));
        t.setId(7);
        when(repo.findById(7)).thenReturn(Optional.of(t));

        assertThat(service.findById(7)).isPresent();
    }

    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(77)).thenReturn(Optional.empty());
        assertThat(service.findById(77)).isEmpty();
    }

    @Test
    void save_persistsEntity() {
        Trade t = new Trade("acc", "type", new BigDecimal("15.00"));
        when(repo.save(any(Trade.class))).thenAnswer(inv -> inv.getArgument(0));

        Trade saved = service.save(t);
        verify(repo).save(t);
        assertThat(saved.getBuyQuantity()).isEqualByComparingTo("15.00");
    }

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

    @Test
    void update_unknown_throws() {
        when(repo.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(999, new Trade()))
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
