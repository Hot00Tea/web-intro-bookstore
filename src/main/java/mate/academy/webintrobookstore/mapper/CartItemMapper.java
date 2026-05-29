package mate.academy.webintrobookstore.mapper;

import mate.academy.webintrobookstore.dto.CartItemResponseDto;
import mate.academy.webintrobookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);

}
