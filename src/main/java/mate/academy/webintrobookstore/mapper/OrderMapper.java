package mate.academy.webintrobookstore.mapper;

import mate.academy.webintrobookstore.dto.OrderItemDto;
import mate.academy.webintrobookstore.dto.OrderResponseDto;
import mate.academy.webintrobookstore.model.Order;
import mate.academy.webintrobookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);
}
