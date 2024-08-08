package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.dto.UserDto;
import com.github.evgenylizogubov.publicvoting.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends BaseMapper<UserDto, UserResponseDto> {
}
