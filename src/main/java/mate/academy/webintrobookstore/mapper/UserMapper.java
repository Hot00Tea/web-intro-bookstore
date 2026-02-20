package mate.academy.webintrobookstore.mapper;

import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
