package ro.example.lab6.service;

import org.springframework.stereotype.Service;
import ro.example.lab6.exception.NotEnoughSeatsException;
import ro.example.lab6.model.Booking;
import ro.example.lab6.model.BookingRequest;
import ro.example.lab6.model.Movie;
import ro.example.lab6.model.Screening;

import java.util.ArrayList;
import java.util.List;

// Logica de business a rezervarilor. Aici NU verificam forma datelor (aia e validarea),
// ci regulile care depind de starea aplicatiei: exista filmul? mai sunt locuri?
@Service
public class BookingService {

    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final List<Booking> bookings = new ArrayList<>();
    private long nextId = 1;

    public BookingService(MovieService movieService, ScreeningService screeningService) {
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    public Booking book(Long movieId, Long screeningId, BookingRequest request) {
        // Ambele metode arunca ResourceNotFoundException daca filmul sau proiectia nu exista.
        Movie movie = movieService.getById(movieId);
        Screening screening = screeningService.getById(movieId, screeningId);

        if (request.getSeats() > screening.getSeatsLeft()) {
            throw new NotEnoughSeatsException("Mai sunt doar " + screening.getSeatsLeft()
                    + " locuri la proiectia " + screeningId + ", ati cerut " + request.getSeats());
        }

        screening.setSeatsLeft(screening.getSeatsLeft() - request.getSeats());
        Booking booking = new Booking(nextId++, movie.getTitle(), screening.getTime(), request.getClientName(),
                request.getSeats(), request.getDate(), request.getSeats() * movie.getPrice());
        bookings.add(booking);
        return booking;
    }
}
