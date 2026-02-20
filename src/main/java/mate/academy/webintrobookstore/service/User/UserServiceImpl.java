package mate.academy.webintrobookstore.service.User;

import lombok.RequiredArgsConstructor;
import mate.academy.spring_security.dto.UserRegistrationRequestDto;
import mate.academy.spring_security.dto.UserResponseDto;
import mate.academy.spring_security.exception.RegistrationException;
import mate.academy.spring_security.mapper.UserMapper;
import mate.academy.spring_security.model.User;
import mate.academy.spring_security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RegistrationException("User with this email already exist");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }
}
