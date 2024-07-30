package com.github.evgenylizogubov.publicvoting.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 20)
    protected String firstName;
    
    @NotBlank
    @Size(min = 2, max = 30)
    protected String lastName;
    
    public NamedTo(Integer id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return super.toString() + '[' + firstName + " " + lastName + ']';
    }
}
