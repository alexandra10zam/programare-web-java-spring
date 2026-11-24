package ro.example.lab7.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.example.lab7.model.Screening;

import java.util.List;

@Repository
public class ScreeningRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScreeningRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===== Exercitiul 4 - JOIN =====

    // TODO 4.1: Scrieti un query cu JOIN intre screenings (s) si movies (m), ca fiecare proiectie
    //   sa aiba si titlul filmului. Selectati coloanele:
    //   s.id, s.movie_id, m.title, s.start_time, s.room, s.seats_left
    //   FROM screenings s JOIN movies m ON m.id = s.movie_id
    //   WHERE s.movie_id = ? ORDER BY s.start_time
    // TODO 4.2: Completati RowMapper-ul de mai jos cu coloanele din query
    //   (new Screening(resultSet.getLong("id"), resultSet.getLong("movie_id"), resultSet.getString("title"), ...)).
    public List<Screening> findByMovie(Long movieId) {
        String sql = "";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> null, movieId);
    }
}
