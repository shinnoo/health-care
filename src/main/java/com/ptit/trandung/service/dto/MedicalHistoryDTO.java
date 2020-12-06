package com.ptit.trandung.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ptit.trandung.domain.MedicalHistory} entity.
 */
public class MedicalHistoryDTO implements Serializable {
    
    private Long id;

    private Instant joinedAt;

    private Instant leavedAt;

    private Long totalPrice;

    private Integer count;

    private Boolean isPaid;

    @Size(min = 1, max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(min = 1, max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;


    private Long doctorId;

    private String doctorName;

    private Long nurseId;

    private String nurseName;

    private Long patientId;

    private String patientName;

    private Long diseaseId;

    private String diseaseName;

    private Long medicineId;

    private String medicineName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Instant getLeavedAt() {
        return leavedAt;
    }

    public void setLeavedAt(Instant leavedAt) {
        this.leavedAt = leavedAt;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalHistoryDTO)) {
            return false;
        }

        return id != null && id.equals(((MedicalHistoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalHistoryDTO{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            ", leavedAt='" + getLeavedAt() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", count=" + getCount() +
            ", isPaid='" + isIsPaid() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", doctorId=" + getDoctorId() +
            ", doctorName='" + getDoctorName() + "'" +
            ", nurseId=" + getNurseId() +
            ", nurseName='" + getNurseName() + "'" +
            ", patientId=" + getPatientId() +
            ", patientName='" + getPatientName() + "'" +
            ", diseaseId=" + getDiseaseId() +
            ", diseaseName='" + getDiseaseName() + "'" +
            ", medicineId=" + getMedicineId() +
            ", medicineName='" + getMedicineName() + "'" +
            "}";
    }
}
