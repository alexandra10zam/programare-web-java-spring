package ro.example.lab3.service;

import org.springframework.stereotype.Service;
import ro.example.lab3.model.Booking;
import ro.example.lab3.model.Movie;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final MovieService movieService;
    private final List<Booking> bookings = new ArrayList<>();
    private long nextId = 1L;

    public BookingService(MovieService movieService) {
        this.movieService = movieService;
    }

    public Booking book(Booking booking) {
        Movie movie = movieService.findById(booking.getMovieId());
        booking.setId(nextId++);
        booking.setMovieTitle(movie.getTitle());
        booking.setTotal(movie.getPrice() * booking.getSeats());
        bookings.add(booking);
        System.out.println("Booking saved: " + booking.getClientName() + " -> " + movie.getTitle());
        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
