package ro.example.fmishop.repository;

import org.springframework.stereotype.Component;
import ro.example.fmishop.model.Product;

import java.util.List;

// The catalog never changes (immutable): it can be shared by the whole application.
@Component
public class ProductCatalog {

    private final List<Product> products = List.of(
            new Product("Laptop", 4500),
            new Product("Mouse", 120),
            new Product("Keyboard", 350),
            new Product("Monitor", 1200));

    public ProductCatalog() {
        System.out.println("   [Spring] ProductCatalog created");
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findByName(String name) {
        return products.stream()
                .filter(product -> product.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown product: " + name));
    }
}
