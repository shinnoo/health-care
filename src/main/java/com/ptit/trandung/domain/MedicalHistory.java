package com.ptit.trandung.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.ptit.trandung.domain.enumeration.MedicalHistoryStatus;

/**
 * A MedicalHistory.
 */
@Entity
@Table(name = "medical_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "joined_at")
    private Instant joinedAt;

    @Column(name = "leaved_at")
    private Instant leavedAt;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "count")
    private Integer count;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MedicalHistoryStatus status;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Size(min = 1, max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(min = 1, max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Doctor doctor;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Nurse nurse;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Disease disease;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Medicine medicine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public MedicalHistory joinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
        return this;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Instant getLeavedAt() {
        return leavedAt;
    }

    public MedicalHistory leavedAt(Instant leavedAt) {
        this.leavedAt = leavedAt;
        return this;
    }

    public void setLeavedAt(Instant leavedAt) {
        this.leavedAt = leavedAt;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public MedicalHistory totalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCount() {
        return count;
    }

    public MedicalHistory count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MedicalHistoryStatus getStatus() {
        return status;
    }

    public MedicalHistory status(MedicalHistoryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MedicalHistoryStatus status) {
        this.status = status;
    }

    public Boolean isIsPaid() {
        return isPaid;
    }

    public MedicalHistory isPaid(Boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MedicalHistory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public MedicalHistory createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public MedicalHistory lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public MedicalHistory lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public MedicalHistory doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public MedicalHistory nurse(Nurse nurse) {
        this.nurse = nurse;
        return this;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Patient getPatient() {
        return patient;
    }

    public MedicalHistory patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Disease getDisease() {
        return disease;
    }

    public MedicalHistory disease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public MedicalHistory medicine(Medicine medicine) {
        this.medicine = medicine;
        return this;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalHistory)) {
            return false;
        }
        return id != null && id.equals(((MedicalHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalHistory{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            ", leavedAt='" + getLeavedAt() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", count=" + getCount() +
            ", status='" + getStatus() + "'" +
            ", isPaid='" + isIsPaid() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
