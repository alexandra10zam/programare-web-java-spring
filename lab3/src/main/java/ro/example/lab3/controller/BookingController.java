package ro.example.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.example.lab3.model.Booking;
import ro.example.lab3.service.BookingService;
import ro.example.lab3.service.MovieService;

@Controller
public class BookingController {

    private final MovieService movieService;
    private final BookingService bookingService;

    public BookingController(MovieService movieService, BookingService bookingService) {
        this.movieService = movieService;
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("movies", movieService.getAllMovies());
        return "booking-form";
    }

    // TODO 2.1: Adaugati @PostMapping pentru ruta /bookings
    public String createBooking(
            // TODO 2.2: Folositi @ModelAttribute("booking") pentru a primi datele din formular
            Booking booking, Model model) {

        // TODO 2.3: Salvati rezervarea cu bookingService.book(booking)
        // TODO 2.4: Adaugati rezervarea salvata in Model cu numele "booking"

        return "booking-confirmation";
    }
}
