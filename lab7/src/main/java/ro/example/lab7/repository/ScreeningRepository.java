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

    // JOIN: fiecare rand din screenings primeste si titlul filmului din movies.
    public List<Screening> findByMovie(Long movieId) {
        String sql = """
                SELECT s.id, s.movie_id, m.title, s.start_time, s.room, s.seats_left
                FROM screenings s
                JOIN movies m ON m.id = s.movie_id
                WHERE s.movie_id = ?
                ORDER BY s.start_time
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Screening(
                resultSet.getLong("id"),
                resultSet.getLong("movie_id"),
                resultSet.getString("title"),
                resultSet.getString("start_time"),
                resultSet.getString("room"),
                resultSet.getInt("seats_left")), movieId);
    }
}
