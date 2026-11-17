package ro.example.lab6.model;

// O proiectie a unui film (din Laboratorul 5). seatsLeft scade la fiecare rezervare.
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

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }
}
