package com.ptit.trandung.service.mapper;


import com.ptit.trandung.domain.*;
import com.ptit.trandung.service.dto.DoctorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doctor} and its DTO {@link DoctorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {



    default Doctor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}
