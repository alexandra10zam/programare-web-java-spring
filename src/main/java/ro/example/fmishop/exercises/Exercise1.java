package ro.example.fmishop.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.fmishop.config.AppConfig;
import ro.example.fmishop.model.ShoppingCart;
import ro.example.fmishop.repository.ProductCatalog;

// Exercise 1 - Singleton vs prototype
public class Exercise1 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // TODO 1.1: Run this class. Is there one catalog or two?
            // Ana and Ion are two different clients: what does each of them have in the cart?
            System.out.println("--- Context created ---");

            ProductCatalog catalog1 = context.getBean(ProductCatalog.class);
            ProductCatalog catalog2 = context.getBean(ProductCatalog.class);
            System.out.println("Same catalog object? " + (catalog1 == catalog2));

            ShoppingCart anaCart = context.getBean(ShoppingCart.class);
            anaCart.addItem(catalog1.findByName("Laptop"));

            ShoppingCart ionCart = context.getBean(ShoppingCart.class);
            ionCart.addItem(catalog1.findByName("Mouse"));

            System.out.println("Ana's cart: " + anaCart.getItems());
            System.out.println("Ion's cart: " + ionCart.getItems());
            System.out.println("Same cart object? " + (anaCart == ionCart));

            // TODO 1.3: After adding @Scope, run again. Count the "[Spring] ShoppingCart created" messages.
        }
    }
}
