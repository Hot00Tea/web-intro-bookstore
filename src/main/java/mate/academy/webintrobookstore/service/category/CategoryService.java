package mate.academy.webintrobookstore.service.category;

import java.util.List;
import mate.academy.webintrobookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.webintrobookstore.dto.CategoryDto;
import mate.academy.webintrobookstore.dto.CreateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto requestDto);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto update(Long id, CreateCategoryRequestDto updateDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
