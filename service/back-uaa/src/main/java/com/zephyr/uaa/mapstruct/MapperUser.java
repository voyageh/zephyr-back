package com.zephyr.uaa.mapstruct;

import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapperUser {

    User toUser(CreateUserDTO dto);

}
