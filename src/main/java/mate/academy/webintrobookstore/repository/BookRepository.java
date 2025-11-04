package mate.academy.webintrobookstore.repository;

import mate.academy.webintrobookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
