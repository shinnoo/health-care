package com.ptit.trandung.service.mapper;


import com.ptit.trandung.domain.*;
import com.ptit.trandung.service.dto.MedicineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medicine} and its DTO {@link MedicineDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicineMapper extends EntityMapper<MedicineDTO, Medicine> {



    default Medicine fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medicine medicine = new Medicine();
        medicine.setId(id);
        return medicine;
    }
}
