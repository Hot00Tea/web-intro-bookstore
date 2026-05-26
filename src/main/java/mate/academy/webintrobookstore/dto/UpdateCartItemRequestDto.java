package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCartItemRequestDto {

    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
