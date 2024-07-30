package com.github.evgenylizogubov.publicvoting.to;

import com.github.evgenylizogubov.publicvoting.HasIdAndEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 128)
    String email;
    
    @NotBlank
    @Size(min = 5, max = 32)
    String password;
    
    Integer points;
    
    public UserTo(Integer id, String firstName, String lastName, String email, String password, Integer points) {
        super(id, firstName, lastName);
        this.email = email;
        this.password = password;
        this.points = points;
    }
    
    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
