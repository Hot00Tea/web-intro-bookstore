package mate.academy.webintrobookstore.service.order;

import java.util.List;
import mate.academy.webintrobookstore.dto.CreateOrderRequestDto;
import mate.academy.webintrobookstore.dto.OrderItemDto;
import mate.academy.webintrobookstore.dto.OrderResponseDto;
import mate.academy.webintrobookstore.dto.UpdateOrderStatusRequestDto;

public interface OrderService {

    OrderResponseDto createOrder(Long userId, CreateOrderRequestDto requestDto);

    List<OrderResponseDto> getOrdersHistory(Long userId);

    OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long itemId);
}
