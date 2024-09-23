package com.github.evgenylizogubov.publicvoting.controller.dto.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
@NoArgsConstructor(force = true)
public class SuggestionRequest extends BaseDto {
    @NotBlank
    private final String description;
}
