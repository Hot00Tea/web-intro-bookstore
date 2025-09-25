package mate.academy.web_intro_bookstore.service;

import mate.academy.web_intro_bookstore.dto.BookDto;
import mate.academy.web_intro_bookstore.dto.CreateBookRequestDto;
import mate.academy.web_intro_bookstore.model.Book;

import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
