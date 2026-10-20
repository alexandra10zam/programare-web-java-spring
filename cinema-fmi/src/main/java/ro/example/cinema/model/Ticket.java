package ro.example.cinema.model;

public record Ticket(String movie, String seat, double price) {

    @Override
    public String toString() {
        return movie + " " + seat;
    }
}
