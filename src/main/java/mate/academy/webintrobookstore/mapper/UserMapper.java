package mate.academy.webintrobookstore.mapper;

import mate.academy.spring_security.dto.UserRegistrationRequestDto;
import mate.academy.spring_security.dto.UserResponseDto;
import mate.academy.spring_security.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
