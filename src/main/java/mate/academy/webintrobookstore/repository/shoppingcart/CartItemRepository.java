package mate.academy.webintrobookstore.repository.shoppingcart;

import java.util.Optional;
import mate.academy.webintrobookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndShoppingCartId(
            Long id,
            Long shoppingCartId
    );
}
