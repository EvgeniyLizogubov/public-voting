package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;

import java.util.Collection;
import java.util.List;

public interface BaseDtoToEntityMapper<E extends BaseDto, T> {
    T toEntity(E dto);
    
    E toDto(T entity);
    
    List<E> toDtoList(Collection<T> entities);
}
