package mate.academy.webintrobookstore.repository.category;

import mate.academy.webintrobookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
