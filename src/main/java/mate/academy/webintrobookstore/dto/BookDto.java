package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {
    private Long id;

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
