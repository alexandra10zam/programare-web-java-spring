package ro.example.lab6.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.example.lab6.model.Booking;
import ro.example.lab6.model.BookingRequest;
import ro.example.lab6.service.BookingService;

// TODO 2.2: Rezervarile sunt o subresursa a proiectiei. Adnotati clasa cu @RestController si
//   @RequestMapping("/api/movies/{movieId}/screenings/{screeningId}/bookings").
public class BookingRestController {

    private final BookingService bookingService;

    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // TODO 2.2 (continuare): Adnotati metoda cu @PostMapping, parametrii movieId si screeningId cu
    //   @PathVariable si parametrul request cu @Valid @RequestBody.
    //   Apelati bookingService.book(movieId, screeningId, request) si returnati 201 Created:
    //   ResponseEntity.status(HttpStatus.CREATED).body(booking)
    public ResponseEntity<Booking> book(Long movieId, Long screeningId, BookingRequest request) {
        return null;
    }
}
