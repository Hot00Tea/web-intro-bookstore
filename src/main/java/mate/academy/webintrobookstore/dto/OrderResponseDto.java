package mate.academy.webintrobookstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import mate.academy.webintrobookstore.model.Status;

@Data
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private List<OrderItemDto> orderItems;

    private LocalDateTime orderDate;

    private BigDecimal total;

    private Status status;
}
