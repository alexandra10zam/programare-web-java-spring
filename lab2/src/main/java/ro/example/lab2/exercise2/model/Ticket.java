package ro.example.lab2.exercise2.model;

public record Ticket(String movie, String seat, double price) {

    @Override
    public String toString() {
        return movie + " " + seat;
    }
}
