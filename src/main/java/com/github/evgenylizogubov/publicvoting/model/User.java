package com.github.evgenylizogubov.publicvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.evgenylizogubov.publicvoting.HasIdAndEmail;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends NamedEntity implements HasIdAndEmail {
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    private String email;
    
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(max = 32)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @Column(name = "points", columnDefinition = "INTEGER DEFAULT 0")
    private int points;
    
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;
    
    public User(Integer id, String firstName, String lastName, String email, String password, int points, Role... roles) {
        this(id, firstName, lastName, email, password, points, Arrays.asList(roles));
    }
    
    public User(Integer id, String firstName, String lastName, String email, String password, int points, Collection<Role> roles) {
        super(id, firstName, lastName);
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
