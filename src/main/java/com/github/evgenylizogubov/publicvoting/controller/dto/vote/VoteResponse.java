package com.github.evgenylizogubov.publicvoting.controller.dto.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class VoteResponse extends BaseDto {
    private final Voting voting;
    private final Suggestion chosenSuggestion;
    private final User user;
}
