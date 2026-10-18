package ro.example.fmishop.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// The cart has STATE that changes: every client needs their own cart.
@Component
// TODO 1.2: Make this bean prototype: @Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ShoppingCart {

    private final List<Product> items = new ArrayList<>();

    public ShoppingCart() {
        System.out.println("   [Spring] ShoppingCart created");
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double total() {
        return items.stream().mapToDouble(Product::price).sum();
    }
}
