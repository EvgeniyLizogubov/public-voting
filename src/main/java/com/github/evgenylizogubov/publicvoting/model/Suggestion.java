package com.github.evgenylizogubov.publicvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suggestion")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion extends BaseEntity {
    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "votind_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Voting voting;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private User user;
}
