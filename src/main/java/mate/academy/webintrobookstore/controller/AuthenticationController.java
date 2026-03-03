package mate.academy.webintrobookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDto registration(@Valid @RequestBody UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}
