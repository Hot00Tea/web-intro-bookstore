package mate.academy.webintrobookstore.service.book;

import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.exception.EntityNotFoundException;
import mate.academy.webintrobookstore.mapper.BookMapper;
import mate.academy.webintrobookstore.model.Book;
import mate.academy.webintrobookstore.repository.book.BookRepository;
import mate.academy.webintrobookstore.repository.book.BooksSpecificationBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BooksSpecificationBuilder booksSpecificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void findById_shouldReturnBookDto() {

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(BigDecimal.valueOf(25.99));
        book.setDescription("Fantasy book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setIsbn("978-1234567890");
        bookDto.setPrice(BigDecimal.valueOf(25.99));
        bookDto.setDescription("Fantasy book");

        when(bookMapper.toDto(book))
                .thenReturn(bookDto);

        BookDto result = bookService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Harry Potter", result.getTitle());
        assertEquals("J.K. Rowling", result.getAuthor());
        assertEquals("978-1234567890", result.getIsbn());
        assertEquals(BigDecimal.valueOf(25.99), result.getPrice());
        assertEquals("Fantasy book", result.getDescription());

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findByIdWithNonExistentId_shouldThrowException() {

        when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(999L)
        );

        verify(bookRepository).findById(999L);
    }

    @Test
    void save_shouldReturnBookDto() {

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Harry Potter");
        requestDto.setAuthor("J.K. Rowling");
        requestDto.setIsbn("978-1234567890");
        requestDto.setPrice(BigDecimal.valueOf(25.99));
        requestDto.setDescription("Fantasy book");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(BigDecimal.valueOf(25.99));
        book.setDescription("Fantasy book");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setIsbn("978-1234567890");
        bookDto.setPrice(BigDecimal.valueOf(25.99));
        bookDto.setDescription("Fantasy book");

        when(bookMapper.toModel(requestDto))
                .thenReturn(book);
        when(bookRepository.save(book))
                .thenReturn(book);
        when(bookMapper.toDto(book))
                .thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);
        assertEquals(1L, result.getId());
        assertEquals("Harry Potter", result.getTitle());
        assertEquals("J.K. Rowling", result.getAuthor());
        assertEquals("978-1234567890", result.getIsbn());
        assertEquals(BigDecimal.valueOf(25.99), result.getPrice());
        assertEquals("Fantasy book", result.getDescription());

        verify(bookMapper).toModel(requestDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void deleteById_shouldDeleteBook() {

        bookService.deleteById(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void findAll_shouldReturnBookDto() {

        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(BigDecimal.valueOf(25.99));
        book.setDescription("Fantasy book");

        List<Book> bookList = new ArrayList<>();

        bookList.add(book);

        Page<Book> bookPage = new PageImpl<>(bookList);

        when(bookRepository.findAll(pageable))
                .thenReturn(bookPage);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setIsbn("978-1234567890");
        bookDto.setPrice(BigDecimal.valueOf(25.99));
        bookDto.setDescription("Fantasy book");

        when(bookMapper.toDto(book))
                .thenReturn(bookDto);

        Page<BookDto> result = bookService.findAll(pageable);

        assertEquals(1L, result.getTotalElements());
        assertEquals("Harry Potter", result.getContent().get(0).getTitle());
        assertEquals("J.K. Rowling", result.getContent().get(0).getAuthor());
        assertEquals("978-1234567890", result.getContent().get(0).getIsbn());
        assertEquals(BigDecimal.valueOf(25.99), result.getContent().get(0).getPrice());
        assertEquals("Fantasy book", result.getContent().get(0).getDescription());

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toDto(book);
    }

    @Test
    void update_shouldReturnUpdatedBookDto() {

        CreateBookRequestDto updateDto = new CreateBookRequestDto();
        updateDto.setTitle("Harry Potter and the Chamber of Secrets");
        updateDto.setPrice(BigDecimal.valueOf(29.99));
        updateDto.setIsbn("125-0135578391");
        updateDto.setDescription("Series books");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(BigDecimal.valueOf(25.99));
        book.setDescription("Fantasy book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(bookRepository.save(book))
                .thenReturn(book);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Harry Potter and the Chamber of Secrets");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setIsbn("125-0135578391");
        bookDto.setPrice(BigDecimal.valueOf(29.99));
        bookDto.setDescription("Series books");

        when(bookMapper.toDto(book))
                .thenReturn(bookDto);

        BookDto result = bookService.update(book.getId(), updateDto);

        assertEquals(1L, result.getId());
        assertEquals("Harry Potter and the Chamber of Secrets", result.getTitle());
        assertEquals(BigDecimal.valueOf(29.99), result.getPrice());
        assertEquals("125-0135578391", result.getIsbn());
        assertEquals("Series books", result.getDescription());

        verify(bookRepository).findById(1L);
        verify(bookMapper).updateBookFromDto(updateDto, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }
}
