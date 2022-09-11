package ru.gb.spring_test.converters;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.gb.spring_test.dto.UserDto;
import ru.gb.spring_test.entities.User;

@Mapper
@Component
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);

    @InheritConfiguration
    UserDto fromUser(User user);
}
