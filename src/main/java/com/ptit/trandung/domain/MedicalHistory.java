package com.ptit.trandung.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

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
            "}";
    }
}
