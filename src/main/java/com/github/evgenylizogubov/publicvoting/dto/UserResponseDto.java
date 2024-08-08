package com.github.evgenylizogubov.publicvoting.dto;

import com.github.evgenylizogubov.publicvoting.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserResponseDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private Integer points;
    private Set<Role> roles;
}
