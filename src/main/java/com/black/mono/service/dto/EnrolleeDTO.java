package com.black.mono.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.black.mono.domain.Enrollee} entity.
 */
public class EnrolleeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean activationStatus;

    @NotNull
    private LocalDate birthDate;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
    private String phoneNumber;

    
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

    public Boolean isActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrolleeDTO)) {
            return false;
        }

        return id != null && id.equals(((EnrolleeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrolleeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activationStatus='" + isActivationStatus() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
