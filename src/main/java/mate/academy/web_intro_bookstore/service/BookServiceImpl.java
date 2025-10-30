package mate.academy.web_intro_bookstore.service;

import lombok.RequiredArgsConstructor;
import mate.academy.web_intro_bookstore.dto.BookDto;
import mate.academy.web_intro_bookstore.dto.CreateBookRequestDto;
import mate.academy.web_intro_bookstore.dto.UpdateBookRequestDto;
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

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto updateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityBotFoundException("Book not found with id: " + id));

        if (updateDto.getTitle() != null) {
            book.setTitle(updateDto.getTitle());
        }

        if (updateDto.getAuthor() != null) {
            book.setAuthor(updateDto.getAuthor());
        }

        if (updateDto.getPrice() != null) {
            book.setPrice(updateDto.getPrice());
        }

        if (updateDto.getDescription() != null) {
            book.setDescription(updateDto.getDescription());
        }

        if (updateDto.getCoverImage() != null) {
            book.setCoverImage(updateDto.getCoverImage());
        }

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }
}
