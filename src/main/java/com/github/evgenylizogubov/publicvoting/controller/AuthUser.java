package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final UserDto userDto;
    
    public AuthUser(@NotNull UserDto userDto) {
        super(userDto.getEmail(), userDto.getPassword(), userDto.getRoles());
        this.userDto = userDto;
    }
    
    public UserDto getUserDto() {
        return userDto;
    }
    
    public boolean hasRole(Role role) {
        return userDto.hasRole(role);
    }
    
    @Override
    public String toString() {
        return "AuthUser:" + userDto.getId() + '[' + userDto.getEmail() + ']';
    }
}
