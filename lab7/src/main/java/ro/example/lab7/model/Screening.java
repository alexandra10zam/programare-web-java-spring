package ro.example.lab7.model;

// O proiectie, impreuna cu titlul filmului (obtinut printr-un JOIN intre screenings si movies).
public class Screening {

    private final Long id;
    private final Long movieId;
    private final String movieTitle;
    private final String time;
    private final String room;
    private final int seatsLeft;

    public Screening(Long id, Long movieId, String movieTitle, String time, String room, int seatsLeft) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.time = time;
        this.room = room;
        this.seatsLeft = seatsLeft;
    }

    public Long getId() {
        return id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }
}
