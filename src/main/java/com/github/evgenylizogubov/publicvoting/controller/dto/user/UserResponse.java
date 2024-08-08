package com.github.evgenylizogubov.publicvoting.controller.dto.user;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserResponse extends BaseDto {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Integer points;
    private final Set<Role> roles;
}
