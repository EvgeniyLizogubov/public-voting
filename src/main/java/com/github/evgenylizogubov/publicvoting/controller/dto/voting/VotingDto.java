package com.github.evgenylizogubov.publicvoting.controller.dto.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class VotingDto extends BaseDto {
    private Theme theme;
    private Boolean isActive;
    private LocalDate startGettingSuggestionsDate;
    private LocalDate startGettingVotesDate;
    private Set<Suggestion> suggestions;
    private Set<Vote> votes;
    private Suggestion winningSuggestion;
}
