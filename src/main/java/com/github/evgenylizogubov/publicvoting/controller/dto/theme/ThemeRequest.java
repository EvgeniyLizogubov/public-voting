package com.github.evgenylizogubov.publicvoting.controller.dto.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ThemeRequest extends BaseDto {
    @NotBlank
    private final String description;
    
    private final Boolean isActive;
}
