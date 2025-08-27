package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/** Tests unitaires du service BidList */
class BidListServiceTest {

	
    private BidListRepository repo;
    private BidListService service;

    
    @BeforeEach
    void setUp() {
        repo = mock(BidListRepository.class);
        service = new BidListServiceImpl(repo);
    }

    
    
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                new BidList("acc1", "type1", 10.0),
                new BidList("acc2", "type2", 20.0)
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(BidList::getAccount)
                .containsExactly("acc1", "acc2");
    }

    
    
    @Test
    void findById_existing_returnsOptionalWithValue() {
        BidList b = new BidList("acc", "type", 5.0);
        b.setId(42);
        when(repo.findById(42)).thenReturn(Optional.of(b));

        assertThat(service.findById(42))
                .isPresent()
                .get()
                .extracting(BidList::getAccount)
                .isEqualTo("acc");
    }

    
    
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThat(service.findById(99)).isEmpty();
    }

    
    
    @Test
    void save_persistsEntity() {
        BidList bid = new BidList("acc", "type", 15.5);
        when(repo.save(any(BidList.class))).thenAnswer(inv -> inv.getArgument(0));

        BidList saved = service.save(bid);

        ArgumentCaptor<BidList> captor = ArgumentCaptor.forClass(BidList.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getAccount()).isEqualTo("acc");
        assertThat(saved.getBidQuantity()).isEqualTo(15.5);
    }

    
    
    @Test
    void update_existing_updatesFields() {
        BidList existing = new BidList("oldAcc", "oldType", 1.0);
        existing.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(BidList.class))).thenAnswer(inv -> inv.getArgument(0));

        BidList toUpdate = new BidList("newAcc", "newType", 99.9);
        BidList updated = service.update(1, toUpdate);

        assertThat(updated.getAccount()).isEqualTo("newAcc");
        assertThat(updated.getBidQuantity()).isEqualTo(99.9);
        verify(repo).save(existing);
    }

    
    
    @Test
    void update_unknownId_throws_andDoesNotSave() {
        when(repo.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999, new BidList()))
                .isInstanceOf(NoSuchElementException.class);

        verify(repo, never()).save(any());
    }

    
    
    @Test
    void deleteById_existing_deletes() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteById(1);
        verify(repo).deleteById(1);
    }

    
    
    @Test
    void deleteById_unknown_throws() {
        when(repo.existsById(123)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteById(123))
                .isInstanceOf(NoSuchElementException.class);

        verify(repo, never()).deleteById(anyInt());
    }
}
