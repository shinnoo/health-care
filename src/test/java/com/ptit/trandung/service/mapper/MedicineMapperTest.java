package com.ptit.trandung.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicineMapperTest {

    private MedicineMapper medicineMapper;

    @BeforeEach
    public void setUp() {
        medicineMapper = new MedicineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicineMapper.fromId(null)).isNull();
    }
}
