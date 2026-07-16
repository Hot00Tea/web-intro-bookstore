package mate.academy.webintrobookstore.service.order;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.CreateOrderRequestDto;
import mate.academy.webintrobookstore.dto.OrderItemDto;
import mate.academy.webintrobookstore.dto.OrderResponseDto;
import mate.academy.webintrobookstore.dto.UpdateOrderStatusRequestDto;
import mate.academy.webintrobookstore.exception.OrderCreationException;
import mate.academy.webintrobookstore.mapper.OrderMapper;
import mate.academy.webintrobookstore.model.CartItem;
import mate.academy.webintrobookstore.model.Order;
import mate.academy.webintrobookstore.model.OrderItem;
import mate.academy.webintrobookstore.model.ShoppingCart;
import mate.academy.webintrobookstore.model.Status;
import mate.academy.webintrobookstore.model.User;
import mate.academy.webintrobookstore.repository.order.OrderItemRepository;
import mate.academy.webintrobookstore.repository.order.OrderRepository;
import mate.academy.webintrobookstore.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.webintrobookstore.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    private Order buildOrder(User user, CreateOrderRequestDto requestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private OrderItem createOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }

    @Override
    public OrderResponseDto createOrder(Long userId, CreateOrderRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(
                "Can't find user by id: " + userId)
        );

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(
                "Can't find shopping cart for user id: " + userId)
        );

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderCreationException("Shopping cart is empty");
        }

        Order order = buildOrder(user, requestDto);
        BigDecimal total = BigDecimal.ZERO;
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : shoppingCart.getCartItems()) {

            OrderItem orderItem = createOrderItem(order, cartItem);

            BigDecimal itemTotal = cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            total = total.add(itemTotal);

            orderItems.add(orderItem);
        }

        order.setTotal(total);
        order.setOrderItems(orderItems);
        order = orderRepository.save(order);
        shoppingCart.getCartItems().clear();

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersHistory(Long userId) {

        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId,
                                              UpdateOrderStatusRequestDto requestDto) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order by id: " + orderId)
        );

        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {

        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order item with id: " + itemId
                                + " for order id: " + orderId)
        );

        return orderMapper.toDto(orderItem);
    }
}
