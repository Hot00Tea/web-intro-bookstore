package mate.academy.webintrobookstore.service.user;

import jakarta.validation.Valid;
import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.exception.RegistrationException;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException;
}
