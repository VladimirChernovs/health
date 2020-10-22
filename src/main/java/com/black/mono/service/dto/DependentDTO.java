package com.black.mono.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.black.mono.domain.Dependent} entity.
 */
public class DependentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;


    private Long enrolleeId;

    private String enrolleeName;
    
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getEnrolleeId() {
        return enrolleeId;
    }

    public void setEnrolleeId(Long enrolleeId) {
        this.enrolleeId = enrolleeId;
    }

    public String getEnrolleeName() {
        return enrolleeName;
    }

    public void setEnrolleeName(String enrolleeName) {
        this.enrolleeName = enrolleeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependentDTO)) {
            return false;
        }

        return id != null && id.equals(((DependentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", enrolleeId=" + getEnrolleeId() +
            ", enrolleeName='" + getEnrolleeName() + "'" +
            "}";
    }
}
