package com.perficient.pbcpuserservice.web.mappers;

import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * User dto <-> entity converter
 * @author tyler.barton
 * @version 1.0, 6/17/2022
 * @project perf-bootcamp-project
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User fromDto(UserDto dto);

    List<UserDto> toDtos(List<User> users);
    List<User> fromDtos(List<UserDto> dtos);

    @Mapping(source="userDto.id", target="id")
    @Mapping(source="userDto.firstName", target="firstName")
    @Mapping(source="userDto.lastName", target="lastName")
    @Mapping(source="userDto.age", target="age")
    @Mapping(source="userDto.gender", target="gender")
    @Mapping(source="userDto.emailAddress", target="emailAddress")
    @Mapping(source="userDto.phoneNumber", target="phoneNumber")
    User updateUser(User user, UserDto userDto);
}
