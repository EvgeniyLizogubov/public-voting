package com.github.evgenylizogubov.publicvoting.web;

import com.github.evgenylizogubov.publicvoting.dto.UserDto;
import com.github.evgenylizogubov.publicvoting.model.Role;
import com.github.evgenylizogubov.publicvoting.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class AuthUser extends org.springframework.security.core.userdetails.User {
    @Getter
    private final UserDto userDto;
    
    public AuthUser(@NotNull UserDto userDto) {
        super(userDto.getEmail(), userDto.getPassword(), userDto.getRoles());
        this.userDto = userDto;
    }
    
    public int id() {
        return userDto.getId();
    }
    
    public static AuthUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return (auth.getPrincipal() instanceof AuthUser authUser) ? authUser : null;
    }
    
    public static AuthUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }
    
    public static UserDto authUser() {
        return get().getUserDto();
    }
    
    public static int authId() {
        return get().id();
    }
    
    public boolean hasRole(Role role) {
        return userDto.hasRole(role);
    }
    
    @Override
    public String toString() {
        return "AuthUser:" + id() + '[' + userDto.getEmail() + ']';
    }
}
