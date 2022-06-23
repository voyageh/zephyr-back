package com.zephyr.uaa.mapstruct;

import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUser {

    User toUser(CreateUserDTO dto);

    CreateUserDTO toDto(User user);
}
