package mate.academy.webintrobookstore.service.user;

import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.exception.RegistrationException;
import mate.academy.webintrobookstore.model.User;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;

    User findByEmail(String email);
}
