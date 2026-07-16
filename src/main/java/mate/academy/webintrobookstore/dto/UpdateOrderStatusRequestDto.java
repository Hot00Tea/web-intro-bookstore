package mate.academy.webintrobookstore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mate.academy.webintrobookstore.model.Status;

@Setter
@Getter
@Data
public class UpdateOrderStatusRequestDto {

    private Status status;
}
