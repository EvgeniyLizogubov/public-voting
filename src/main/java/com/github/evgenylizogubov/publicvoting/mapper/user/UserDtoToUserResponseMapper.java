package com.github.evgenylizogubov.publicvoting.mapper.user;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoToUserResponseMapper extends BaseDtoToResponseMapper<UserDto, UserResponse> {
}
