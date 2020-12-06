package com.ptit.trandung.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicalHistoryMapperTest {

    private MedicalHistoryMapper medicalHistoryMapper;

    @BeforeEach
    public void setUp() {
        medicalHistoryMapper = new MedicalHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicalHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicalHistoryMapper.fromId(null)).isNull();
    }
}
