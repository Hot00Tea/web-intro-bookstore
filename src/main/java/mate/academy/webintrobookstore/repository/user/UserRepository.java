package mate.academy.webintrobookstore.repository.user;

import mate.academy.webintrobookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
