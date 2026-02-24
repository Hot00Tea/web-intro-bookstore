package mate.academy.webintrobookstore.service.user;

import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.exception.RegistrationException;
import mate.academy.webintrobookstore.mapper.UserMapper;
import mate.academy.webintrobookstore.model.User;
import mate.academy.webintrobookstore.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with this email already exist");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }
}
