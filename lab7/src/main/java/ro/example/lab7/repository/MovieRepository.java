package ro.example.lab7.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.example.lab7.model.Movie;

import java.util.List;
import java.util.Optional;

// Stratul care vorbeste cu baza de date. Inlocuieste ArrayList-ul din laboratoarele trecute.
@Repository
public class MovieRepository {

    // RowMapper: transforma UN rand din ResultSet intr-un obiect Movie.
    // JdbcTemplate il apeleaza pentru fiecare rand si face singur while (resultSet.next()).
    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = (resultSet, rowNum) -> new Movie(
            resultSet.getLong("id"),
            resultSet.getString("title"),
            resultSet.getString("genre"),
            resultSet.getDouble("price"));

    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===== Exercitiul 2 - Citire cu JdbcTemplate =====

    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT id, title, genre, price FROM movies ORDER BY id", MOVIE_ROW_MAPPER);
    }

    public List<Movie> findByGenre(String genre) {
        return jdbcTemplate.query("SELECT id, title, genre, price FROM movies WHERE genre = ? ORDER BY id",
                MOVIE_ROW_MAPPER, genre);
    }

    // query() intoarce o lista (goala daca nu exista filmul); queryForObject() ar arunca o exceptie.
    public Optional<Movie> findById(Long id) {
        return jdbcTemplate.query("SELECT id, title, genre, price FROM movies WHERE id = ?",
                MOVIE_ROW_MAPPER, id).stream().findFirst();
    }

    // ===== Exercitiul 3 - INSERT, UPDATE, DELETE =====

    // RETURNING id: PostgreSQL intoarce id-ul generat, pe care il citim cu queryForObject().
    public Movie save(Movie movie) {
        Long id = jdbcTemplate.queryForObject(
                "INSERT INTO movies (title, genre, price) VALUES (?, ?, ?) RETURNING id",
                Long.class, movie.getTitle(), movie.getGenre(), movie.getPrice());
        movie.setId(id);
        return movie;
    }

    // update() intoarce numarul de randuri modificate: 0 inseamna ca filmul nu exista.
    public int update(Long id, Movie movie) {
        return jdbcTemplate.update("UPDATE movies SET title = ?, genre = ?, price = ? WHERE id = ?",
                movie.getTitle(), movie.getGenre(), movie.getPrice(), id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM movies WHERE id = ?", id);
    }
}
