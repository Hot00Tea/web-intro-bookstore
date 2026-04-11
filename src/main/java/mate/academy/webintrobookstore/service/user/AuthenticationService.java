package mate.academy.webintrobookstore.service.user;

import mate.academy.webintrobookstore.dto.UserLoginRequestDto;
import mate.academy.webintrobookstore.dto.UserLoginResponseDto;

public interface AuthenticationService {

    UserLoginResponseDto authenticate(UserLoginRequestDto request);
}
