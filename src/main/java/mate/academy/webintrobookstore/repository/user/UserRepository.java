package mate.academy.webintrobookstore.repository.user;

import mate.academy.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase (String email);
}
