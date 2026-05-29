package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddBookToCartRequestDto {

    @NotNull
    @Positive
    private Long bookId;

    @Positive
    private int quantity;
}
