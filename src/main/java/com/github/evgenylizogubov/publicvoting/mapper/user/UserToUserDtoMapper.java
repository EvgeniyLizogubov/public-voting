package com.github.evgenylizogubov.publicvoting.mapper.user;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseMapper;
import com.github.evgenylizogubov.publicvoting.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToUserDtoMapper extends BaseMapper<User, UserDto> {
}
