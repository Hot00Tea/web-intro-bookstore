package mate.academy.web_intro_bookstore.repository;

import mate.academy.web_intro_bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
