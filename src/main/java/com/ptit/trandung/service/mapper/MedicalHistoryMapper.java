package com.ptit.trandung.service.mapper;


import com.ptit.trandung.domain.*;
import com.ptit.trandung.service.dto.MedicalHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalHistory} and its DTO {@link MedicalHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, NurseMapper.class, PatientMapper.class, DiseaseMapper.class, MedicineMapper.class})
public interface MedicalHistoryMapper extends EntityMapper<MedicalHistoryDTO, MedicalHistory> {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.name", target = "doctorName")
    @Mapping(source = "nurse.id", target = "nurseId")
    @Mapping(source = "nurse.name", target = "nurseName")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "disease.id", target = "diseaseId")
    @Mapping(source = "disease.name", target = "diseaseName")
    @Mapping(source = "medicine.id", target = "medicineId")
    @Mapping(source = "medicine.name", target = "medicineName")
    MedicalHistoryDTO toDto(MedicalHistory medicalHistory);

    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "nurseId", target = "nurse")
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "diseaseId", target = "disease")
    @Mapping(source = "medicineId", target = "medicine")
    MedicalHistory toEntity(MedicalHistoryDTO medicalHistoryDTO);

    default MedicalHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(id);
        return medicalHistory;
    }
}
