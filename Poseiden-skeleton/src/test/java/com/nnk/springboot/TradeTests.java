package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeTests {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void trade_crud_ok() {
        // Create & Save (ton entité a un constructeur (account, type, buyQuantity))
        Trade trade = new Trade("Trade Account", "Type", 100d);
        trade = tradeRepository.save(trade);

        assertThat(trade.getId()).isNotNull();
        assertThat(trade.getAccount()).isEqualTo("Trade Account");

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertThat(trade.getAccount()).isEqualTo("Trade Account Update");

        // Find all
        List<Trade> listResult = tradeRepository.findAll();
        assertThat(listResult).isNotEmpty();

        // Delete
        Integer id = trade.getId();
        tradeRepository.delete(trade);
        Optional<Trade> afterDelete = tradeRepository.findById(id);
        assertThat(afterDelete).isNotPresent();
    }
}
