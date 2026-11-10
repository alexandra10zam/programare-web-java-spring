package ro.example.lab5.model;

// Resursa principala a API-ului.
// version creste la fiecare modificare (PUT sau PATCH). Il folosim ca ETag la Exercitiul 3.
public class Movie {

    private Long id;
    private String title;
    private String genre;
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
