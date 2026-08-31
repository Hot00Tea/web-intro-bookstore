package mate.academy.webintrobookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.security.JwtUtil;
import mate.academy.webintrobookstore.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser(roles = "USER")
    void findById_shouldReturnBook() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setIsbn("978-1234567890");
        bookDto.setPrice(BigDecimal.valueOf(25.99));
        bookDto.setDescription("Fantasy book");

        when(bookService.findById(1L))
                .thenReturn(bookDto);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Harry Potter"))
                .andExpect(jsonPath("$.author").value("J.K. Rowling"))
                .andExpect(jsonPath("$.isbn").value("978-1234567890"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(25.99)))
                .andExpect(jsonPath("$.description").value("Fantasy book"));

        verify(bookService).findById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_shouldReturnCreatedBook() throws Exception {

        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("Harry Potter");
        createBookRequestDto.setAuthor("JK Rowling");
        createBookRequestDto.setIsbn("978-1234567890");
        createBookRequestDto.setPrice(BigDecimal.valueOf(25.99));
        createBookRequestDto.setDescription("Fantasy book");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("JK Rowling");
        bookDto.setIsbn("978-1234567890");
        bookDto.setPrice(BigDecimal.valueOf(25.99));
        bookDto.setDescription("Fantasy book");

        when(bookService.save(createBookRequestDto)).thenReturn(bookDto);

        mockMvc.perform(post("/books").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Harry Potter"))
                .andExpect(jsonPath("$.author").value("JK Rowling"))
                .andExpect(jsonPath("$.isbn").value("978-1234567890"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(25.99)))
                .andExpect(jsonPath("$.description").value("Fantasy book"));

        verify(bookService).save(createBookRequestDto);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook_shouldReturnNoContent() throws Exception {

        mockMvc.perform(delete("/books/1").with(csrf()))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(1L);
    }
}