package com.github.evgenylizogubov.publicvoting.controller.dto.user;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.mapper.Default;
import com.github.evgenylizogubov.publicvoting.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer points;
    private Set<Role> roles;
    
    @Default
    public UserDto(Integer id, String firstName, String lastName, String email, String password, Integer points, Role... roles) {
        this(id, firstName, lastName, email, password, points, Arrays.asList(roles));
    }
    
    public UserDto(Integer id, String firstName, String lastName, String email, String password, Integer points, Collection<Role> roles) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.points = points;
        setRoles(roles);
    }
    
    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
    
    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
}
