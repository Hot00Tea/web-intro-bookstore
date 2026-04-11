package mate.academy.webintrobookstore.service.category;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.webintrobookstore.dto.CategoryDto;
import mate.academy.webintrobookstore.dto.CreateCategoryRequestDto;
import mate.academy.webintrobookstore.exception.EntityNotFoundException;
import mate.academy.webintrobookstore.mapper.BookMapper;
import mate.academy.webintrobookstore.mapper.CategoryMapper;
import mate.academy.webintrobookstore.model.Category;
import mate.academy.webintrobookstore.repository.book.BookRepository;
import mate.academy.webintrobookstore.repository.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CreateCategoryRequestDto updateDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found with id: " + id));

        categoryMapper.updateEntityFromDto(updateDto, category);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        findById(id);
        return bookRepository.findAllByCategoriesId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
