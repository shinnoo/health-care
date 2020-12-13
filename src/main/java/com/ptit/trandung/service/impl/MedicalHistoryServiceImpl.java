package com.ptit.trandung.service.impl;

import com.ptit.trandung.domain.Doctor;
import com.ptit.trandung.domain.Nurse;
import com.ptit.trandung.domain.enumeration.MedicalHistoryStatus;
import com.ptit.trandung.repository.DoctorRepository;
import com.ptit.trandung.repository.NurseRepository;
import com.ptit.trandung.service.MedicalHistoryService;
import com.ptit.trandung.domain.MedicalHistory;
import com.ptit.trandung.repository.MedicalHistoryRepository;
import com.ptit.trandung.service.dto.MedicalHistoryDTO;
import com.ptit.trandung.service.mapper.MedicalHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicalHistory}.
 */
@Service
@Transactional
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

    private final MedicalHistoryRepository medicalHistoryRepository;

    private final MedicalHistoryMapper medicalHistoryMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    NurseRepository nurseRepository;

    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository, MedicalHistoryMapper medicalHistoryMapper) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalHistoryMapper = medicalHistoryMapper;
    }

    @Override
    public MedicalHistoryDTO save(MedicalHistoryDTO medicalHistoryDTO) {
        log.debug("Request to save MedicalHistory : {}", medicalHistoryDTO);
        MedicalHistory medicalHistory = medicalHistoryMapper.toEntity(medicalHistoryDTO);
        if (MedicalHistoryStatus.DONE.equals(medicalHistory.getStatus()) && !medicalHistory.isIsPaid()){
            Doctor doctor = medicalHistory.getDoctor();
            doctor.setSalary(doctor.getSalary()+1000000);
            doctorRepository.save(doctor);

            Nurse nurse = medicalHistory.getNurse();
            nurse.setSalary(nurse.getSalary()+200000);
            nurseRepository.save(nurse);
        }
        medicalHistory = medicalHistoryRepository.save(medicalHistory);
        return medicalHistoryMapper.toDto(medicalHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalHistories");
        return medicalHistoryRepository.findAll(pageable)
            .map(medicalHistoryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistoryDTO> findOne(Long id) {
        log.debug("Request to get MedicalHistory : {}", id);
        return medicalHistoryRepository.findById(id)
            .map(medicalHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalHistory : {}", id);
        medicalHistoryRepository.deleteById(id);
    }

    @Override
    public Page<MedicalHistoryDTO> getAllByPatientId(Long userId, Pageable pageable) {
        return medicalHistoryRepository.findAllByPatientId(userId,pageable)
            .map(medicalHistoryMapper::toDto);
    }

    // ngày đầu của mỗi tháng
    @Scheduled(cron = "0 0 0 1 1/1 ?")
    public void calculatedSalary() {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(doctor -> {
            doctor.setSalary(7000000l);
        });
        doctorRepository.saveAll(doctors);

        List<Nurse> nurses = nurseRepository.findAll();
        nurses.forEach(nurse -> {
            nurse.setSalary(7000000l);
        });
        nurseRepository.saveAll(nurses);
    }
}
