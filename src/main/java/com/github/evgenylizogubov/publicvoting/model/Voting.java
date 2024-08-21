package com.github.evgenylizogubov.publicvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "voting")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Voting extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Theme theme;
    
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isActive = false;
    
    @Column(name = "start_getting_suggestions_date")
    private LocalDate startGettingSuggestionsDate;
    
    @Column(name = "start_getting_votes_date")
    private LocalDate startGettingVotesDate;
    
    @OneToMany(mappedBy = "voting")
    private Set<Suggestion> suggestions;
    
    @OneToMany(mappedBy = "voting")
    private Set<Vote> votes;
}
