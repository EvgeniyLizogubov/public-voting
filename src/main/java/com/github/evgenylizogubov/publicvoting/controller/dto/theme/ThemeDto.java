package com.github.evgenylizogubov.publicvoting.controller.dto.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeDto extends BaseDto {
    private String description;
    private Boolean isUsed;
}
