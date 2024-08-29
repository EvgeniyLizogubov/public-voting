package com.github.evgenylizogubov.publicvoting.mapper.theme;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeRequestToThemeDtoMapper extends BaseMapper<ThemeDto, ThemeRequest> {
    @Override
    ThemeDto toEntity(ThemeRequest dto);
}
