package com.ptit.trandung.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "level")
    private String level;

    @Column(name = "experience")
    private String experience;

    @Column(name = "type")
    private String type;

    @Column(name = "academic_level")
    private String academicLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public Doctor idCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public Doctor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Doctor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Doctor dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Doctor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public Doctor level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExperience() {
        return experience;
    }

    public Doctor experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getType() {
        return type;
    }

    public Doctor type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public Doctor academicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
        return this;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctor{" +
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
