package mate.academy.webintrobookstore.service;

import java.util.List;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.BookSearchParameters;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto updateDto);

    List<BookDto> search(BookSearchParameters params);
}
