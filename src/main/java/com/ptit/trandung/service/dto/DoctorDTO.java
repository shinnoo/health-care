package com.ptit.trandung.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ptit.trandung.domain.Doctor} entity.
 */
public class DoctorDTO implements Serializable {
    
    private Long id;

    private String idCard;

    private String name;

    private String code;

    private String dateOfBirth;

    private String address;

    private String level;

    private String experience;

    private String type;

    private String academicLevel;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorDTO)) {
            return false;
        }

        return id != null && id.equals(((DoctorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorDTO{" +
            "id=" + getId() +
            ", idCard='" + getIdCard() + "'" +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", address='" + getAddress() + "'" +
            ", level='" + getLevel() + "'" +
            ", experience='" + getExperience() + "'" +
            ", type='" + getType() + "'" +
            ", academicLevel='" + getAcademicLevel() + "'" +
            "}";
    }
}
