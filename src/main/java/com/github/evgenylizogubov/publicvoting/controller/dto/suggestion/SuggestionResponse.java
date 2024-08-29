package com.github.evgenylizogubov.publicvoting.controller.dto.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class SuggestionResponse extends BaseDto {
    private final String description;
    private final Voting voting;
    private final User user;
}
