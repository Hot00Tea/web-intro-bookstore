package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {

    @NotBlank
    private String name;

    private String description;
}
