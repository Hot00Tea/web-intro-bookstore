package mate.academy.webintrobookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.exception.EntityNotFoundException;
import mate.academy.webintrobookstore.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:database/create/add-default-categories.sql",
        "classpath:database/create/add-default-books.sql",
        "classpath:database/create/add-into-books-categories-table.sql"
})
@Sql(
        scripts = {
                "classpath:database/delete/delete-books-categories-table.sql",
                "classpath:database/delete/delete-all-books.sql",
                "classpath:database/delete/delete-all-categories.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAll_shouldReturnBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title")
                        .value("Harry Potter"))
                .andExpect(jsonPath("$.content[0].author")
                        .value("J.K. Rowling"))
                .andExpect(jsonPath("$.content[0].isbn")
                        .value("978-1234567890"))
                .andExpect(jsonPath("$.content[0].price")
                        .value(25.99))
                .andExpect(jsonPath("$.content[0].description")
                        .value("Fantasy book"));
    }

    @Test
    void findAll_withoutUserRole_shouldReturnForbidden()
            throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findById_shouldReturnBook() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value("Harry Potter"))
                .andExpect(jsonPath("$.author")
                        .value("J.K. Rowling"))
                .andExpect(jsonPath("$.isbn")
                        .value("978-1234567890"))
                .andExpect(jsonPath("$.price")
                        .value(25.99))
                .andExpect(jsonPath("$.description")
                        .value("Fantasy book"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void findById_withNonExistentId_shouldThrowException()
            throws Exception {
        ServletException exception = org.junit.jupiter.api.Assertions
                .assertThrows(
                        ServletException.class,
                        () -> mockMvc.perform(get("/books/999"))
                );

        assertTrue(
                exception.getCause() instanceof EntityNotFoundException
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_shouldReturnCreatedBook() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        requestDto.setTitle("The Lord of the Rings");
        requestDto.setAuthor("JRR Tolkien");
        requestDto.setIsbn("978-9876543210");
        requestDto.setPrice(BigDecimal.valueOf(30.99));
        requestDto.setDescription("Fantasy book");

        mockMvc.perform(
                        post("/books")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title")
                        .value("The Lord of the Rings"))
                .andExpect(jsonPath("$.author")
                        .value("JRR Tolkien"))
                .andExpect(jsonPath("$.isbn")
                        .value("978-9876543210"))
                .andExpect(jsonPath("$.price")
                        .value(30.99))
                .andExpect(jsonPath("$.description")
                        .value("Fantasy book"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createBook_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        requestDto.setTitle("The Lord of the Rings");
        requestDto.setAuthor("JRR Tolkien");
        requestDto.setIsbn("978-9876543210");
        requestDto.setPrice(BigDecimal.valueOf(30.99));
        requestDto.setDescription("Fantasy book");

        mockMvc.perform(
                        post("/books")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_withInvalidData_shouldReturnBadRequest()
            throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        mockMvc.perform(
                        post("/books")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook_shouldReturnNoContent() throws Exception {
        mockMvc.perform(
                        delete("/books/1")
                                .with(csrf())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteBook_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        mockMvc.perform(
                        delete("/books/1")
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateBook_shouldReturnUpdatedBook() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        requestDto.setTitle(
                "Harry Potter and the Chamber of Secrets"
        );
        requestDto.setAuthor("JRR Rowling");
        requestDto.setIsbn("978-1111111111");
        requestDto.setPrice(BigDecimal.valueOf(29.99));
        requestDto.setDescription("Updated fantasy book");

        mockMvc.perform(
                        put("/books/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value(
                                "Harry Potter and the Chamber of Secrets"
                        ))
                .andExpect(jsonPath("$.author")
                        .value("JRR Rowling"))
                .andExpect(jsonPath("$.isbn")
                        .value("978-1111111111"))
                .andExpect(jsonPath("$.price")
                        .value(29.99))
                .andExpect(jsonPath("$.description")
                        .value("Updated fantasy book"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateBook_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        requestDto.setTitle("Updated Book");
        requestDto.setAuthor("JRR Rowling");
        requestDto.setIsbn("978-2222222222");
        requestDto.setPrice(BigDecimal.valueOf(29.99));
        requestDto.setDescription("Updated book");

        mockMvc.perform(
                        put("/books/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void search_shouldReturnBooks() throws Exception {
        mockMvc.perform(get("/books/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title")
                        .value("Harry Potter"))
                .andExpect(jsonPath("$[0].author")
                        .value("J.K. Rowling"))
                .andExpect(jsonPath("$[0].isbn")
                        .value("978-1234567890"));
    }
}