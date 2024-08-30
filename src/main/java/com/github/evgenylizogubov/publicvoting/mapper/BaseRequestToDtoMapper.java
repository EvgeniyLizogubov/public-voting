package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import org.mapstruct.MappingTarget;

public interface BaseRequestToDtoMapper<E extends BaseDto, T extends BaseDto> {
    T toDto(E request);
    
    T updateFromRequest(@MappingTarget T dto, E request);
}
