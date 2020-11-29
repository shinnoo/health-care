package com.ptit.trandung.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.ptit.trandung.domain.Disease} entity.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
public class DiseaseDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiseaseDTO)) {
            return false;
        }

        return id != null && id.equals(((DiseaseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiseaseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
