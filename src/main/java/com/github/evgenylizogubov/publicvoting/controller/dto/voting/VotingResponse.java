package com.github.evgenylizogubov.publicvoting.controller.dto.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value
public class VotingResponse extends BaseDto {
    private final Theme theme;
    private final Boolean isActive;
    private final LocalDate startGettingSuggestionsDate;
    private final LocalDate startGettingVotesDate;
    private final Set<Suggestion> suggestions;
    private final Set<Vote> votes;
    private final Suggestion winningSuggestion;
}
