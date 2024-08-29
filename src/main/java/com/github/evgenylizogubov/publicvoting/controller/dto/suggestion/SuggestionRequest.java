package com.github.evgenylizogubov.publicvoting.controller.dto.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class SuggestionRequest extends BaseDto {
    @NotBlank
    private final String description;
    
    @NotNull
    private final Voting voting;
}
