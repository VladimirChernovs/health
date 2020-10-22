package com.black.mono.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Enrollee.
 */
@Entity
@Table(name = "enrollee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enrollee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "activation_status", nullable = false)
    private Boolean activationStatus;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "enrollee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Dependent> dependents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Enrollee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActivationStatus() {
        return activationStatus;
    }

    public Enrollee activationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
        return this;
    }

    public void setActivationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Enrollee birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Enrollee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Dependent> getDependents() {
        return dependents;
    }

    public Enrollee dependents(Set<Dependent> dependents) {
        this.dependents = dependents;
        return this;
    }

    public Enrollee addDependent(Dependent dependent) {
        this.dependents.add(dependent);
        dependent.setEnrollee(this);
        return this;
    }

    public Enrollee removeDependent(Dependent dependent) {
        this.dependents.remove(dependent);
        dependent.setEnrollee(null);
        return this;
    }

    public void setDependents(Set<Dependent> dependents) {
        this.dependents = dependents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enrollee)) {
            return false;
        }
        return id != null && id.equals(((Enrollee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enrollee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activationStatus='" + isActivationStatus() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
