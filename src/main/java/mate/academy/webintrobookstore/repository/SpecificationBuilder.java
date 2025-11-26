package mate.academy.webintrobookstore.repository;

import mate.academy.webintrobookstore.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;


public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameters);
}
