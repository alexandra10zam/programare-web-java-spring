package ro.example.lab5.model;

// O proiectie a unui film: subresursa a filmului.
// URI: /api/movies/{movieId}/screenings/{screeningId}
public class Screening {

    private Long id;
    private Long movieId;
    private String time;
    private String room;
    private int seatsLeft;

    public Screening(Long id, Long movieId, String time, String room, int seatsLeft) {
        this.id = id;
        this.movieId = movieId;
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
