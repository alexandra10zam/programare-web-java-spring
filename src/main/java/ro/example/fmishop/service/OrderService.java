package ro.example.fmishop.service;

import org.springframework.stereotype.Service;
import ro.example.fmishop.model.ShoppingCart;
import ro.example.fmishop.repository.ProductCatalog;

// OrderService is a SINGLETON (the default scope).
@Service
public class OrderService {

    private final ProductCatalog catalog;
    private final ShoppingCart cart;
    private final PaymentService paymentService;
    private final InvoicePdfGenerator invoiceGenerator;

    // TODO 2.2: The cart is injected only ONCE, when this singleton is created.
    // Replace the ShoppingCart field with ObjectProvider<ShoppingCart> cartProvider
    // and get a new cart inside checkout():  ShoppingCart cart = cartProvider.getObject();

    // TODO 3.3: Do the same for InvoicePdfGenerator: use ObjectProvider<InvoicePdfGenerator>
    // and call getObject() only inside generateInvoice().

    public OrderService(ProductCatalog catalog,
                        ShoppingCart cart,
                        PaymentService paymentService,
                        InvoicePdfGenerator invoiceGenerator) {
        System.out.println("   [Spring] OrderService created");
        this.catalog = catalog;
        this.cart = cart;
        this.paymentService = paymentService;
        this.invoiceGenerator = invoiceGenerator;
    }

    public String checkout(String client, String... productNames) {
        for (String name : productNames) {
            cart.addItem(catalog.findByName(name));
        }
        System.out.println("   " + client + "'s cart: " + cart.getItems()
                + " (cart #" + cart.hashCode() + ")");
        return paymentService.pay(client, cart.total());
    }

    public void generateInvoice(String receipt) {
        invoiceGenerator.generate(receipt);
    }
}
