package com.ptit.trandung.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ptit.trandung.web.rest.TestUtil;

public class MedicineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineDTO.class);
        MedicineDTO medicineDTO1 = new MedicineDTO();
        medicineDTO1.setId(1L);
        MedicineDTO medicineDTO2 = new MedicineDTO();
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
        medicineDTO2.setId(medicineDTO1.getId());
        assertThat(medicineDTO1).isEqualTo(medicineDTO2);
        medicineDTO2.setId(2L);
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
        medicineDTO1.setId(null);
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
    }
}
