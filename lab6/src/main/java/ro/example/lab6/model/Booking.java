package ro.example.lab6.model;

import java.time.LocalDate;

// Rezervarea creata de server (raspunsul cererii POST .../bookings).
public class Booking {

    private final Long id;
    private final String movieTitle;
    private final String screeningTime;
    private final String clientName;
    private final int seats;
    private final LocalDate date;
    private final double total;

    public Booking(Long id, String movieTitle, String screeningTime, String clientName,
                   int seats, LocalDate date, double total) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.screeningTime = screeningTime;
        this.clientName = clientName;
        this.seats = seats;
        this.date = date;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getScreeningTime() {
        return screeningTime;
    }

    public String getClientName() {
        return clientName;
    }

    public int getSeats() {
        return seats;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }
}
