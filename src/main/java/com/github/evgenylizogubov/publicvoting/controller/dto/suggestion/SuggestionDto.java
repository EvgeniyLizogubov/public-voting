package com.github.evgenylizogubov.publicvoting.controller.dto.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionDto extends BaseDto {
    private String description;
    private Voting voting;
    private User user;
}
