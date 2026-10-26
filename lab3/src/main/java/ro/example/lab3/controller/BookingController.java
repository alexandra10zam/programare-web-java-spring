package ro.example.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/bookings")
    public String createBooking(@ModelAttribute("booking") Booking booking, Model model) {
        Booking saved = bookingService.book(booking);
        model.addAttribute("booking", saved);
        return "booking-confirmation";
    }
}
