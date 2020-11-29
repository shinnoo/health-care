package com.ptit.trandung.service.mapper;


import com.ptit.trandung.domain.*;
import com.ptit.trandung.service.dto.NurseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nurse} and its DTO {@link NurseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NurseMapper extends EntityMapper<NurseDTO, Nurse> {



    default Nurse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nurse nurse = new Nurse();
        nurse.setId(id);
        return nurse;
    }
}
