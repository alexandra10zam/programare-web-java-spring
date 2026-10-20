package ro.example.cinema.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String pay(String client, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        System.out.println("      >> PaymentService: charging " + amount + " RON from " + client);
        return "RECEIPT-" + client.toUpperCase() + "-" + (int) amount;
    }
}
