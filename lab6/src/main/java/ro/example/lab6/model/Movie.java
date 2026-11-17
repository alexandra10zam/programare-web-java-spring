package ro.example.lab6.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Resursa principala a API-ului (din Laboratorul 5).
// Constrangerile de validare stau direct pe campuri: le verifica Spring cand primeste un body cu @Valid.
public class Movie {

    private Long id;

    @NotBlank(message = "Titlul este obligatoriu")
    @Size(max = 40, message = "Titlul poate avea cel mult 40 de caractere")
    private String title;

    @NotBlank(message = "Genul este obligatoriu")
    private String genre;

    @Positive(message = "Pretul trebuie sa fie pozitiv")
    private double price;

    private int version = 1;

    public Movie() {
    }

    public Movie(Long id, String title, String genre, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
