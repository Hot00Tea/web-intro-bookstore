package mate.academy.webintrobookstore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.dto.UpdateBookRequestDto;
import mate.academy.webintrobookstore.exception.EntityBotFoundException;
import mate.academy.webintrobookstore.mapper.BookMapper;
import mate.academy.webintrobookstore.model.Book;
import mate.academy.webintrobookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityBotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto updateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityBotFoundException("Book not found with id: " + id));

        bookMapper.updateBookFromDto(updateDto, book);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }
}
