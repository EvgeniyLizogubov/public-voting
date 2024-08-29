package com.github.evgenylizogubov.publicvoting.mapper.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeDtoToResponseMapper extends BaseMapper<ThemeDto, ThemeResponse> {
}
