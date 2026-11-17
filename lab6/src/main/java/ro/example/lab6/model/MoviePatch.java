package ro.example.lab6.model;

// Body-ul unei cereri PATCH (din Laboratorul 5): campurile lipsa raman null.
public class MoviePatch {

    private String title;
    private String genre;
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
