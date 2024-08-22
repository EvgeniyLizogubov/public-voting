package com.github.evgenylizogubov.publicvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "voting_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Voting voting;
    
    @ManyToOne
    @JoinColumn(name = "suggestion_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Suggestion chosenSuggestion;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private User user;
}
