package mate.academy.webintrobookstore.repository.category;

import mate.academy.webintrobookstore.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveCategory_shouldFindCategoryById() {

        Category category = new Category();
        category.setName("Fantasy");
        category.setDescription("Fantasy books");
        categoryRepository.save(category);

        Optional<Category> result = categoryRepository.findById(category.getId());
        assertTrue(result.isPresent());
        assertEquals("Fantasy", result.get().getName());
    }

    @Test
    void findByIdWithNonExistentId_shouldReturnEmptyResult() {

        Optional<Category> result = categoryRepository.findById(2L);
        assertTrue(result.isEmpty());

    }
}
