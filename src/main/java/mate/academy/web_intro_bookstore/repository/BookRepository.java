package mate.academy.web_intro_bookstore.repository;

import mate.academy.web_intro_bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
