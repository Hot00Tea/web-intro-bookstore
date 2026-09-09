package mate.academy.webintrobookstore.service.category;

import mate.academy.webintrobookstore.dto.CategoryDto;
import mate.academy.webintrobookstore.dto.CreateCategoryRequestDto;
import mate.academy.webintrobookstore.exception.EntityNotFoundException;
import mate.academy.webintrobookstore.mapper.BookMapper;
import mate.academy.webintrobookstore.mapper.CategoryMapper;
import mate.academy.webintrobookstore.model.Category;
import mate.academy.webintrobookstore.repository.book.BookRepository;
import mate.academy.webintrobookstore.repository.category.CategoryRepository;
import mate.academy.webintrobookstore.service.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findById_shouldReturnCategoryDto() {

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");
        category.setDescription("Fantasy books");

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Fantasy");
        categoryDto.setDescription("Fantasy books");

        when(categoryMapper.toDto(category))
                .thenReturn(categoryDto);

        CategoryDto result = categoryService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Fantasy", result.getName());
        assertEquals("Fantasy books", result.getDescription());

        verify(categoryRepository).findById(1L);
        verify(categoryMapper).toDto(category);
    }

    @Test
    void findByIdWithNonExistentId_shouldThrowException() {

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(999L)
        );
    }

    @Test
    void save_shouldReturnCategoryDto() {

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Fantasy");
        requestDto.setDescription("Fantasy books");

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");
        category.setDescription("Fantasy books");

        when(categoryMapper.toModel(requestDto))
                .thenReturn(category);

        when(categoryRepository.save(category))
                .thenReturn(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Fantasy");
        categoryDto.setDescription("Fantasy books");

        when(categoryMapper.toDto(category))
                .thenReturn(categoryDto);

        CategoryDto result = categoryService.save(requestDto);

        assertEquals(1L, result.getId());
        assertEquals("Fantasy", result.getName());
        assertEquals("Fantasy books", result.getDescription());

        verify(categoryMapper).toModel(requestDto);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(category);

    }

    @Test
    void update_shouldReturnUpdatedCategoryDto() {

        CreateCategoryRequestDto updateDto = new CreateCategoryRequestDto();
        updateDto.setName("Horror");
        updateDto.setDescription("Horror books");

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");
        category.setDescription("Fantasy books");

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        when(categoryRepository.save(category))
                .thenReturn(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Horror");
        categoryDto.setDescription("Horror books");

        when(categoryMapper.toDto(category))
                .thenReturn(categoryDto);

        CategoryDto result = categoryService.update(category.getId(), updateDto);

        assertEquals(1L, result.getId());
        assertEquals("Horror", result.getName());
        assertEquals("Horror books", result.getDescription());

        verify(categoryRepository).findById(1L);
        verify(categoryMapper).updateEntityFromDto(updateDto, category);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(category);
    }

    @Test
    void findAll_shouldReturnCategoryDto() {

        Pageable pageable = PageRequest.of(0, 10);

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");
        category.setDescription("Fantasy books");

        List<Category> categoryList = new ArrayList<>();

        categoryList.add(category);

        Page<Category> categoryPage = new PageImpl<>(categoryList);

        when(categoryRepository.findAll(pageable))
                .thenReturn(categoryPage);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Fantasy");
        categoryDto.setDescription("Fantasy books");

        when(categoryMapper.toDto(category))
                .thenReturn(categoryDto);

        Page<CategoryDto> result = categoryService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Fantasy", result.getContent().get(0).getName());
        assertEquals("Fantasy books", result.getContent().get(0).getDescription());

        verify(categoryRepository).findAll(pageable);
        verify(categoryMapper).toDto(category);
    }
}
