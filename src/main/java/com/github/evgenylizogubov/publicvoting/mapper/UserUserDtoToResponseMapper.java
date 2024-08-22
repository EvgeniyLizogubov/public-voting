package com.github.evgenylizogubov.publicvoting.mapper;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserUserDtoToResponseMapper extends BaseMapper<UserDto, UserResponse> {
}
