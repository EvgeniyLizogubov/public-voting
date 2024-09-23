package com.github.evgenylizogubov.publicvoting.controller.dto.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDto extends BaseDto {
    private Voting voting;
    private Suggestion chosenSuggestion;
    private User user;
}
