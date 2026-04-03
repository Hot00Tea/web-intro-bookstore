package mate.academy.webintrobookstore.mapper;

import mate.academy.webintrobookstore.dto.CategoryDto;
import mate.academy.webintrobookstore.dto.CreateCategoryRequestDto;
import mate.academy.webintrobookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto dto);

    void updateEntityFromDto(CreateCategoryRequestDto dto, @MappingTarget Category category);
}
