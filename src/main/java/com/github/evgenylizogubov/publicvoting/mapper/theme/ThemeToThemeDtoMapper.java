package com.github.evgenylizogubov.publicvoting.mapper.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseMapper;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeToThemeDtoMapper extends BaseMapper<Theme, ThemeDto> {
}
