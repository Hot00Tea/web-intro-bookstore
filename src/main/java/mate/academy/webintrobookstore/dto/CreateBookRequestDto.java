package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import mate.academy.webintrobookstore.validation.Author;

@Data
public class CreateBookRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title length must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Author
    private String author;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Cover image is required")
    private String coverImage;
}


