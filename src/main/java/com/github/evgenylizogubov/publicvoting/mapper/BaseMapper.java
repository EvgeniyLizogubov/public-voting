package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.dto.BaseDto;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<E, T extends BaseDto> {
    E toEntity(T to);
    
    List<E> toEntityList(Collection<T> tos);
    
    E updateFromDto(@MappingTarget E entity, T to);
    
    T toDto(E entity);
    
    List<T> toDtoList(Collection<E> entities);
}
