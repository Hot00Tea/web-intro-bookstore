package mate.academy.webintrobookstore.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.model.Book;
import mate.academy.webintrobookstore.repository.SpecificationProvider;
import mate.academy.webintrobookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification provider or key: " + key));
    }
}
