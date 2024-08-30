package com.github.evgenylizogubov.publicvoting.controller.dto.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class VotingRequest extends BaseDto {
    @NotNull
    private final Theme theme;
}
