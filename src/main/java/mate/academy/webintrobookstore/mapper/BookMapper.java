package mate.academy.webintrobookstore.mapper;

import mate.academy.webintrobookstore.config.MapperConfig;
import mate.academy.webintrobookstore.dto.BookDto;
import mate.academy.webintrobookstore.dto.CreateBookRequestDto;
import mate.academy.webintrobookstore.dto.UpdateBookRequestDto;
import mate.academy.webintrobookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(UpdateBookRequestDto updateDto, @MappingTarget Book book);
}
