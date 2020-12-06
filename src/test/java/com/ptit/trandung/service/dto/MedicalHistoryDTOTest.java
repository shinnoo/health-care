package com.ptit.trandung.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ptit.trandung.web.rest.TestUtil;

public class MedicalHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistoryDTO.class);
        MedicalHistoryDTO medicalHistoryDTO1 = new MedicalHistoryDTO();
        medicalHistoryDTO1.setId(1L);
        MedicalHistoryDTO medicalHistoryDTO2 = new MedicalHistoryDTO();
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO2.setId(medicalHistoryDTO1.getId());
        assertThat(medicalHistoryDTO1).isEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO2.setId(2L);
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO1.setId(null);
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
    }
}
