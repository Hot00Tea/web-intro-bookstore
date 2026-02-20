package mate.academy.webintrobookstore.controller;

import jakarta.validation.Valid;
import mate.academy.spring_security.dto.UserRegistrationRequestDto;
import mate.academy.spring_security.dto.UserResponseDto;
import mate.academy.spring_security.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public UserResponseDto registration(@Valid @RequestBody UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}
