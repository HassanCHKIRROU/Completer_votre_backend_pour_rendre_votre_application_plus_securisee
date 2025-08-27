package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidTests {
	

    @Autowired
    private BidListRepository bidListRepository;

    
    
    @Test
    void bidList_crud_ok() {
        // Create & Save
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        bid = bidListRepository.save(bid);
        assertThat(bid.getId()).isNotNull();
        assertThat(bid.getBidQuantity()).isEqualTo(10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        assertThat(bid.getBidQuantity()).isEqualTo(20d);

        // Find all
        List<BidList> listResult = bidListRepository.findAll();
        assertThat(listResult).isNotEmpty();

        // Delete
        Integer id = bid.getId();
        bidListRepository.delete(bid);
        Optional<BidList> afterDelete = bidListRepository.findById(id);
        assertThat(afterDelete).isNotPresent();
    }
}
