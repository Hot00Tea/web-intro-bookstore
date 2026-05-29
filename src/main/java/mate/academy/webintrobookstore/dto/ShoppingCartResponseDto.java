package mate.academy.webintrobookstore.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShoppingCartResponseDto {

    private Long id;

    private Long userId;

    private List<CartItemResponseDto> cartItems;
}
