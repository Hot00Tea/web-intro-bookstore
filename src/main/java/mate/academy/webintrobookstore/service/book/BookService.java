package mate.academy.webintrobookstore.service.book;

import java.util.List;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.BookSearchParameters;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    List<BookDto> getAll();

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto updateDto);

    List<BookDto> search(BookSearchParameters params);
}
