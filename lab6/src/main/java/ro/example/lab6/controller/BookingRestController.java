package ro.example.lab6.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.example.lab6.model.Booking;
import ro.example.lab6.model.BookingRequest;
import ro.example.lab6.service.BookingService;

// Rezervarile unei proiectii: subresursa a proiectiei, care e subresursa a filmului.
@RestController
@RequestMapping("/api/movies/{movieId}/screenings/{screeningId}/bookings")
public class BookingRestController {

    private final BookingService bookingService;

    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // POST /api/movies/1/screenings/1/bookings -> 201 Created
    @PostMapping
    public ResponseEntity<Booking> book(@PathVariable Long movieId,
                                        @PathVariable Long screeningId,
                                        @Valid @RequestBody BookingRequest request) {
        Booking booking = bookingService.book(movieId, screeningId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}
