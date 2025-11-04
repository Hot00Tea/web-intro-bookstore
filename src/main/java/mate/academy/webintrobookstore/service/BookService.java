package mate.academy.webintrobookstore.service;

import java.util.List;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.dto.UpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, UpdateBookRequestDto updateDto);
}
