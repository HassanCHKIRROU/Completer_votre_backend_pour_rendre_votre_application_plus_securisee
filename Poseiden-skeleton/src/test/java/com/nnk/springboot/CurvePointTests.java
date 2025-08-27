package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointTests {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    void curvePoint_crud_ok() {
        // Create & Save (constructeur: term, value)
        CurvePoint curvePoint = new CurvePoint(10d, 30d);
        curvePoint.setCurveId(10);
        curvePoint = curvePointRepository.save(curvePoint);

        assertThat(curvePoint.getId()).isNotNull();
        assertThat(curvePoint.getCurveId()).isEqualTo(10);
        assertThat(curvePoint.getTerm()).isEqualTo(10d);
        assertThat(curvePoint.getValue()).isEqualTo(30d);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        assertThat(curvePoint.getCurveId()).isEqualTo(20);

        // Find all
        List<CurvePoint> listResult = curvePointRepository.findAll();
        assertThat(listResult).isNotEmpty();

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePoint> afterDelete = curvePointRepository.findById(id);
        assertThat(afterDelete).isNotPresent();
    }
}
