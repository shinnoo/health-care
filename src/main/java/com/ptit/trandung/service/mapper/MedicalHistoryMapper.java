package com.ptit.trandung.service.mapper;


import com.ptit.trandung.domain.*;
import com.ptit.trandung.service.dto.MedicalHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalHistory} and its DTO {@link MedicalHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalHistoryMapper extends EntityMapper<MedicalHistoryDTO, MedicalHistory> {



    default MedicalHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(id);
        return medicalHistory;
    }
}
