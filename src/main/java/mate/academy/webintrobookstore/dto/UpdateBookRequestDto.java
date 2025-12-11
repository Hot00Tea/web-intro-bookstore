package mate.academy.webintrobookstore.dto;

import java.math.BigDecimal;
import lombok.Data;
import mate.academy.webintrobookstore.validation.Author;

@Data
public class UpdateBookRequestDto {
    private String title;

    @Author
    private String author;

    private BigDecimal price;

    private String description;

    private String coverImage;
}
