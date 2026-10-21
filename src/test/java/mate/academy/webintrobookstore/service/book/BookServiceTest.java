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

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Harry Potter");
        expected.setAuthor("J.K. Rowling");
        expected.setIsbn("978-1234567890");
        expected.setPrice(BigDecimal.valueOf(25.99));
        expected.setDescription("Fantasy book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(bookMapper.toDto(book))
                .thenReturn(expected);

        BookDto result = bookService.getBookById(1L);

        assertEquals(expected, result);

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findByIdWithNonExistentId_shouldThrowException() {
        when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getBookById(999L)
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

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Harry Potter");
        expected.setAuthor("J.K. Rowling");
        expected.setIsbn("978-1234567890");
        expected.setPrice(BigDecimal.valueOf(25.99));
        expected.setDescription("Fantasy book");

        when(bookMapper.toModel(requestDto))
                .thenReturn(book);

        when(bookRepository.save(book))
                .thenReturn(book);

        when(bookMapper.toDto(book))
                .thenReturn(expected);

        BookDto result = bookService.createBook(requestDto);

        assertEquals(expected, result);

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
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("978-1234567890");
        book.setPrice(BigDecimal.valueOf(25.99));
        book.setDescription("Fantasy book");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Harry Potter");
        expected.setAuthor("J.K. Rowling");
        expected.setIsbn("978-1234567890");
        expected.setPrice(BigDecimal.valueOf(25.99));
        expected.setDescription("Fantasy book");

        when(bookRepository.findAll())
                .thenReturn(bookList);

        when(bookMapper.toDto(book))
                .thenReturn(expected);

        List<BookDto> result = bookService.getAll();

        assertEquals(1, result.size());
        assertEquals(expected, result.get(0));

        verify(bookRepository).findAll();
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

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Harry Potter and the Chamber of Secrets");
        expected.setAuthor("J.K. Rowling");
        expected.setIsbn("125-0135578391");
        expected.setPrice(BigDecimal.valueOf(29.99));
        expected.setDescription("Series books");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(bookRepository.save(book))
                .thenReturn(book);

        when(bookMapper.toDto(book))
                .thenReturn(expected);

        BookDto result = bookService.update(book.getId(), updateDto);

        assertEquals(expected, result);

        verify(bookRepository).findById(1L);
        verify(bookMapper).updateBookFromDto(updateDto, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }
}
