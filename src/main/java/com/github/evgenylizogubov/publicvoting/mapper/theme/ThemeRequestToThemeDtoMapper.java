package com.github.evgenylizogubov.publicvoting.mapper.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseRequestToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeRequestToThemeDtoMapper extends BaseRequestToDtoMapper<ThemeRequest, ThemeDto> {
}
