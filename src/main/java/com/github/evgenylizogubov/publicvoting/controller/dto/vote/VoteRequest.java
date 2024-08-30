package com.github.evgenylizogubov.publicvoting.controller.dto.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class VoteRequest extends BaseDto {
    @NotNull
    private final Voting voting;
    
    @NotNull
    private final Theme theme;
    
    @NotNull
    private final Suggestion chosenSuggestion;
}
