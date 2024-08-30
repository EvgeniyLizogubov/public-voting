package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;

import java.util.Collection;
import java.util.List;

public interface BaseDtoToResponseMapper<E extends BaseDto, T extends BaseDto> {
    T toResponse(E dto);
    
    List<T> toResponseList(Collection<E> dto);
}
