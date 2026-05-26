package mate.academy.webintrobookstore.repository.shoppingcart;

import java.util.Optional;
import mate.academy.webintrobookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        JpaSpecificationExecutor<CartItem> {

    Optional<CartItem> findByShoppingCartIdAndBookId(Long cartId, Long bookId);

}
