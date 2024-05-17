package ru.sermyazhko.Springsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sermyazhko.Springsecurity.dto.UserDTO;
import ru.sermyazhko.Springsecurity.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    List<UserDTO> userListToUserDTOList(List<User> users);
}
