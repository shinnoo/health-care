package com.ptit.trandung.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ptit.trandung.web.rest.TestUtil;

public class NurseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NurseDTO.class);
        NurseDTO nurseDTO1 = new NurseDTO();
        nurseDTO1.setId(1L);
        NurseDTO nurseDTO2 = new NurseDTO();
        assertThat(nurseDTO1).isNotEqualTo(nurseDTO2);
        nurseDTO2.setId(nurseDTO1.getId());
        assertThat(nurseDTO1).isEqualTo(nurseDTO2);
        nurseDTO2.setId(2L);
        assertThat(nurseDTO1).isNotEqualTo(nurseDTO2);
        nurseDTO1.setId(null);
        assertThat(nurseDTO1).isNotEqualTo(nurseDTO2);
    }
}
