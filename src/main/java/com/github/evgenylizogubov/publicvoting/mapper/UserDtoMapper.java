package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends BaseMapper<UserDto, UserRequest> {
    @Mapping(target = "email", expression = "java(dto.getEmail().toLowerCase())")
    @Mapping(target = "roles", expression = "java({Role.USER})")
    @Override
    UserDto toEntity(UserRequest dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(dto.getEmail().toLowerCase())")
    @Override
    UserDto updateFromDto(@MappingTarget UserDto entity, UserRequest dto);
}
