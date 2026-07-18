package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.webintrobookstore.model.Status;

@Data
public class UpdateOrderStatusRequestDto {

    @NotNull
    private Status status;
}
