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

    // TODO 2.1: Adaugati constrangerile de validare (vezi tabelul din curs):
    //   clientName: obligatoriu, intre 3 si 50 de caractere -> @NotBlank, @Size(min = 3, max = 50)
    //   phone: obligatoriu, de forma 07xxxxxxxx -> @NotBlank, @Pattern(regexp = "07[0-9]{8}")
    //   seats: intre 1 si 10 -> @Min(1), @Max(10)
    //   date: obligatorie si in viitor -> @NotNull, @Future
    //   Dati fiecarei constrangeri un mesaj clar, de exemplu:
    //   @NotBlank(message = "Numele este obligatoriu")
    private String clientName;

    private String phone;

    private int seats;

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
