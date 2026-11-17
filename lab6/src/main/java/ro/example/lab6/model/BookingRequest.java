package ro.example.lab6.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// Ce trimite clientul cand rezerva locuri (body-ul cererii POST .../bookings).
public class BookingRequest {

    @NotBlank(message = "Numele este obligatoriu")
    @Size(min = 3, max = 50, message = "Numele trebuie sa aiba intre 3 si 50 de caractere")
    private String clientName;

    // @Pattern nu verifica valorile null, de aceea avem nevoie si de @NotBlank.
    @NotBlank(message = "Telefonul este obligatoriu")
    @Pattern(regexp = "07[0-9]{8}", message = "Telefonul trebuie sa aiba forma 07xxxxxxxx")
    private String phone;

    @Min(value = 1, message = "Rezervati cel putin un loc")
    @Max(value = 10, message = "Puteti rezerva cel mult 10 locuri")
    private int seats;

    @NotNull(message = "Data este obligatorie")
    @Future(message = "Data trebuie sa fie in viitor")
    private LocalDate date;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
