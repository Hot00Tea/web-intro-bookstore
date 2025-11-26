package mate.academy.webintrobookstore.repository;


public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);

}
