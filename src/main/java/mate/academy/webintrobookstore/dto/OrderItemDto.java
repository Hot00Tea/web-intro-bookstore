package mate.academy.webintrobookstore.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;

    private Long bookId;

    private int quantity;
}
