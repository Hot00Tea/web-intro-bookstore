package mate.academy.webintrobookstore.dto;

import java.math.BigDecimal;
import lombok.Setter;

@Setter
public class BookDto {
    private String title;

    private String author;

    private String isbn;

    private BigDecimal price;

    private String description;

    private String coverImage;

}
