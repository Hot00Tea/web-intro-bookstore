package mate.academy.web_intro_bookstore.dto;

import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class BookDto {
    private String title;

    private String author;

    private String isbn;

    private BigDecimal price;

    private String description;

    private String coverImage;

}
