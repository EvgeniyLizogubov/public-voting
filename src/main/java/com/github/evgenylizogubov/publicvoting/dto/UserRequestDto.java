package com.github.evgenylizogubov.publicvoting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserRequestDto extends BaseDto {
    @NotBlank
    @Size(min = 2, max = 20)
    private final String firstName;
    
    @NotBlank
    @Size(min = 2, max = 30)
    private final String lastName;
    
    @Email
    @NotBlank
    @Size(max = 128)
    private final String email;
    
    @NotBlank
    @Size(min = 3, max = 32)
    private final String password;
}
