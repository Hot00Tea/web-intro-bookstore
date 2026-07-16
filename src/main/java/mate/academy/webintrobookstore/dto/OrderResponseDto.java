package mate.academy.webintrobookstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mate.academy.webintrobookstore.model.Status;

@Setter
@Getter
@Data
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private List<OrderItemDto> orderItems;

    private LocalDateTime orderDate;

    private BigDecimal total;

    private Status status;
}
