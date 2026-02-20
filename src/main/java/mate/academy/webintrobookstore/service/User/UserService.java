package mate.academy.webintrobookstore.service.User;

import jakarta.validation.Valid;
import mate.academy.spring_security.dto.UserRegistrationRequestDto;
import mate.academy.spring_security.dto.UserResponseDto;
import mate.academy.spring_security.exception.RegistrationException;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
        throws RegistrationException;
}
