package mate.academy.webintrobookstore.service.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.UserRegistrationRequestDto;
import mate.academy.webintrobookstore.dto.UserResponseDto;
import mate.academy.webintrobookstore.exception.RegistrationException;
import mate.academy.webintrobookstore.mapper.UserMapper;
import mate.academy.webintrobookstore.model.Role;
import mate.academy.webintrobookstore.model.RoleName;
import mate.academy.webintrobookstore.model.User;
import mate.academy.webintrobookstore.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.webintrobookstore.repository.user.RoleRepository;
import mate.academy.webintrobookstore.repository.user.UserRepository;
import mate.academy.webintrobookstore.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with email "
                    + request.getEmail()
                    + " already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role: "
                        + RoleName.ROLE_USER + "not found"));
        user.setRoles(Set.of(userRole));
        user = userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return userMapper.toDto(user);
    }
}
