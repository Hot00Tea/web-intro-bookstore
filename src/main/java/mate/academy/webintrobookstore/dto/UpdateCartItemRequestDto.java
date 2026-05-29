package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCartItemRequestDto {

    @Positive
    private int quantity;
}
