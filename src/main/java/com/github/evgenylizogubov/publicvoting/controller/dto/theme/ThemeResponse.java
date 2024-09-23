package com.github.evgenylizogubov.publicvoting.controller.dto.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ThemeResponse extends BaseDto {
    private final String description;
    private final Boolean isUsed;
}
