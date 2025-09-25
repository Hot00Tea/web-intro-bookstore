package mate.academy.web_intro_bookstore.service;

import lombok.RequiredArgsConstructor;
import mate.academy.web_intro_bookstore.dto.BookDto;
import mate.academy.web_intro_bookstore.dto.CreateBookRequestDto;
import mate.academy.web_intro_bookstore.exception.EntityBotFoundException;
import mate.academy.web_intro_bookstore.mapper.BookMapper;
import mate.academy.web_intro_bookstore.model.Book;
import mate.academy.web_intro_bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
}
