package ro.example.lab4.model;

// Resursa expusa de API. Spring o transforma automat in JSON (si inapoi):
// are nevoie de constructorul fara parametri, de getteri si de setteri.
public class Movie {

    private Long id;
    private String title;
    private String genre;
    private double price;

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
}
