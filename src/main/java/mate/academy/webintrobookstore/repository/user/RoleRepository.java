package mate.academy.webintrobookstore.repository.user;

import java.util.Optional;
import mate.academy.webintrobookstore.model.Role;
import mate.academy.webintrobookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
