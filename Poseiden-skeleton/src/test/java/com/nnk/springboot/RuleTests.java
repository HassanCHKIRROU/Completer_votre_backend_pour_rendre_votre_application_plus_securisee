package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleTests {
	

    @Autowired
    private RuleNameRepository ruleNameRepository;

    
    
    @Test
    void rule_crud_ok() {
        // Create & Save
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule = ruleNameRepository.save(rule);

        assertThat(rule.getId()).isNotNull();
        assertThat(rule.getName()).isEqualTo("Rule Name");

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        assertThat(rule.getName()).isEqualTo("Rule Name Update");

        // Find all
        List<RuleName> listResult = ruleNameRepository.findAll();
        assertThat(listResult).isNotEmpty();

        // Delete
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> afterDelete = ruleNameRepository.findById(id);
        assertThat(afterDelete).isNotPresent();
    }
}
