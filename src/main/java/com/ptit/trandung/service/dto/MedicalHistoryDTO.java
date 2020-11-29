package com.ptit.trandung.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ptit.trandung.domain.MedicalHistory} entity.
 */
public class MedicalHistoryDTO implements Serializable {
    
    private Long id;

    private Instant joinedAt;

    private Instant leavedAt;

    private Long totalPrice;

    
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
            "}";
    }
}
