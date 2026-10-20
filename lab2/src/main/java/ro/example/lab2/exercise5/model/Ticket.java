package ro.example.lab2.exercise5.model;

public record Ticket(String movie, String seat, double price) {

    @Override
    public String toString() {
        return movie + " " + seat;
    }
}
