package com.github.evgenylizogubov.publicvoting.mapper.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToEntityMapper;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeDtoToThemeMapper extends BaseDtoToEntityMapper<ThemeDto, Theme> {
}
