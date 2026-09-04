package mate.academy.webintrobookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.webintrobookstore.dto.CreateCategoryRequestDto;
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
class CategoryControllerTest {

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
    void findAll_shouldReturnCategories() throws Exception {
        mockMvc.perform(get("/categories?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name")
                        .value("Fantasy"))
                .andExpect(jsonPath("$.content[0].description")
                        .value("Fantasy books"));
    }

    @Test
    void findAll_withoutUserRole_shouldReturnForbidden()
            throws Exception {
        mockMvc.perform(get("/categories?page=0&size=10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCategoryById_shouldReturnCategory() throws Exception {
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Fantasy"))
                .andExpect(jsonPath("$.description")
                        .value("Fantasy books"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getBooksByCategoryId_shouldReturnBooks()
            throws Exception {
        mockMvc.perform(get("/categories/1/books"))
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

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory_shouldReturnCreatedCategory()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        requestDto.setName("Science Fiction");
        requestDto.setDescription("Science fiction books");

        mockMvc.perform(
                        post("/categories")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name")
                        .value("Science Fiction"))
                .andExpect(jsonPath("$.description")
                        .value("Science fiction books"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createCategory_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        requestDto.setName("Science Fiction");
        requestDto.setDescription("Science fiction books");

        mockMvc.perform(
                        post("/categories")
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
    void createCategory_withInvalidData_shouldReturnBadRequest()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        mockMvc.perform(
                        post("/categories")
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
    void updateCategory_shouldReturnUpdatedCategory()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        requestDto.setName("Updated Fantasy");
        requestDto.setDescription("Updated fantasy books");

        mockMvc.perform(
                        put("/categories/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        requestDto
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Updated Fantasy"))
                .andExpect(jsonPath("$.description")
                        .value("Updated fantasy books"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateCategory_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        requestDto.setName("Updated Fantasy");
        requestDto.setDescription("Updated fantasy books");

        mockMvc.perform(
                        put("/categories/1")
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
    void updateCategory_withInvalidData_shouldReturnBadRequest()
            throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto();

        mockMvc.perform(
                        put("/categories/1")
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
    @Sql("classpath:database/create/add-category-for-delete.sql")
    void deleteCategory_shouldReturnNoContent()
            throws Exception {
        mockMvc.perform(
                        delete("/categories/2")
                                .with(csrf())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteCategory_withoutAdminRole_shouldReturnForbidden()
            throws Exception {
        mockMvc.perform(
                        delete("/categories/1")
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }
}
