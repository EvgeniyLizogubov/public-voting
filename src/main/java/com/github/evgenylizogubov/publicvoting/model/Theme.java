package com.github.evgenylizogubov.publicvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "theme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme extends BaseEntity {
    @Column(name = "description", nullable = false, unique = true)
    @NotBlank
    private String description;
}
