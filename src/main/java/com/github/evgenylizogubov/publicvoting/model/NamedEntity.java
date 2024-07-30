package com.github.evgenylizogubov.publicvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NamedEntity extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;
    
    protected NamedEntity(Integer id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return super.toString() + '[' + firstName + " " + lastName + ']';
    }
}
