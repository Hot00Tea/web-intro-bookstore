package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Setter;

@Setter
public class BookDto {
    @NotNull
    private String title;
    @NotNull
    private String author;

    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;

    private String description;

    private String coverImage;

}
