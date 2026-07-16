package mate.academy.webintrobookstore.repository.order;

import java.util.List;
import mate.academy.webintrobookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);
}
