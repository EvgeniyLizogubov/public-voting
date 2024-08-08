package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.dto.UserDto;
import com.github.evgenylizogubov.publicvoting.dto.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends BaseMapper<UserDto, UserRequestDto> {
    @Mapping(target = "email", expression = "java(dto.getEmail().toLowerCase())")
    @Mapping(target = "roles", expression = "java({Role.USER})")
    @Override
    UserDto toEntity(UserRequestDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(dto.getEmail().toLowerCase())")
    @Override
    UserDto updateFromDto(@MappingTarget UserDto entity, UserRequestDto dto);
}
