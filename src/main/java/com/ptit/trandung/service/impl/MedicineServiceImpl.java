package com.ptit.trandung.service.impl;

import com.ptit.trandung.service.MedicineService;
import com.ptit.trandung.domain.Medicine;
import com.ptit.trandung.repository.MedicineRepository;
import com.ptit.trandung.service.dto.MedicineDTO;
import com.ptit.trandung.service.mapper.MedicineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Medicine}.
 */
@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final Logger log = LoggerFactory.getLogger(MedicineServiceImpl.class);

    private final MedicineRepository medicineRepository;

    private final MedicineMapper medicineMapper;

    public MedicineServiceImpl(MedicineRepository medicineRepository, MedicineMapper medicineMapper) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
    }

    @Override
    public MedicineDTO save(MedicineDTO medicineDTO) {
        log.debug("Request to save Medicine : {}", medicineDTO);
        Medicine medicine = medicineMapper.toEntity(medicineDTO);
        medicine = medicineRepository.save(medicine);
        return medicineMapper.toDto(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medicines");
        return medicineRepository.findAll(pageable)
            .map(medicineMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineDTO> findOne(Long id) {
        log.debug("Request to get Medicine : {}", id);
        return medicineRepository.findById(id)
            .map(medicineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medicine : {}", id);
        medicineRepository.deleteById(id);
    }
}
