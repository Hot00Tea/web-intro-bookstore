package mate.academy.webintrobookstore.service.user;

import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;
}
