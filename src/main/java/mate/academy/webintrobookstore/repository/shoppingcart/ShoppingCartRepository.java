package mate.academy.webintrobookstore.repository.shoppingcart;

import java.util.Optional;
import mate.academy.webintrobookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserId(Long userId);
}
