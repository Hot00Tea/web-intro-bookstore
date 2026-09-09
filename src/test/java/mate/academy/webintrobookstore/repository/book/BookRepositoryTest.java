package mate.academy.webintrobookstore.repository.book;

import mate.academy.webintrobookstore.model.Book;
import mate.academy.webintrobookstore.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllByCategoriesId_shouldReturnBooksByCategory() {

        Category category = new Category();
        category.setName("Fantasy");
        category.setDescription("Fantasy books");

        entityManager.persist(category);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(new BigDecimal("25.99"));
        book.setDescription("Fantasy book");
        book.getCategories().add(category);
        bookRepository.save(book);

        List<Book> result = bookRepository.findAllByCategoriesId(category.getId());

        assertEquals(1, result.size());
        assertEquals(book.getId(), result.get(0).getId());
    }
}


