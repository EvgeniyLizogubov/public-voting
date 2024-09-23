package com.github.evgenylizogubov.publicvoting.mapper.user;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseRequestToDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserRequestToUserDtoMapper extends BaseRequestToDtoMapper<UserRequest, UserDto> {
    @Mapping(target = "email", expression = "java(request.getEmail().toLowerCase())")
    @Mapping(target = "roles", expression = "java({Role.USER})")
    @Override
    UserDto toDto(UserRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(request.getEmail().toLowerCase())")
    @Override
    UserDto updateFromRequest(@MappingTarget UserDto dto, UserRequest request);
}
