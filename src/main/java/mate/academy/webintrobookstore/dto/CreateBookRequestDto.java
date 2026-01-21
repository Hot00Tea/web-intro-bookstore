package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import mate.academy.webintrobookstore.validation.Author;

@Data
public class CreateBookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    @Author
    private String author;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    private String description;

    private String coverImage;
}


