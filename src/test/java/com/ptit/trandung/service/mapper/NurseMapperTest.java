package com.ptit.trandung.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NurseMapperTest {

    private NurseMapper nurseMapper;

    @BeforeEach
    public void setUp() {
        nurseMapper = new NurseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(nurseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nurseMapper.fromId(null)).isNull();
    }
}
