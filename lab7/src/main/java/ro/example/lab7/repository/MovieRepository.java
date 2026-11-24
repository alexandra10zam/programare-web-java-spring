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

    // TODO 2.1: Completati RowMapper-ul: transforma UN rand din ResultSet intr-un Movie.
    //   new Movie(resultSet.getLong("id"), resultSet.getString("title"),
    //             resultSet.getString("genre"), resultSet.getDouble("price"))
    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = (resultSet, rowNum) -> null;

    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===== Exercitiul 2 - Citire cu JdbcTemplate =====

    // TODO 2.2: Returnati toate filmele, ordonate dupa id:
    //   jdbcTemplate.query("SELECT id, title, genre, price FROM movies ORDER BY id", MOVIE_ROW_MAPPER)
    public List<Movie> findAll() {
        throw new UnsupportedOperationException("TODO 2.2: implementati findAll");
    }

    // TODO 2.3: La fel, dar cu WHERE genre = ? . Valoarea parametrului se da dupa RowMapper:
    //   jdbcTemplate.query(sql, MOVIE_ROW_MAPPER, genre)
    public List<Movie> findByGenre(String genre) {
        throw new UnsupportedOperationException("TODO 2.3: implementati findByGenre");
    }

    // TODO 2.4: Cautati filmul dupa id (WHERE id = ?). jdbcTemplate.query intoarce o lista:
    //   returnati primul element, sau Optional.empty() daca lista e goala -> .stream().findFirst()
    public Optional<Movie> findById(Long id) {
        throw new UnsupportedOperationException("TODO 2.4: implementati findById");
    }

    // ===== Exercitiul 3 - INSERT, UPDATE, DELETE =====

    // TODO 3.1: Inserati filmul si lasati PostgreSQL sa genereze id-ul:
    //   Long id = jdbcTemplate.queryForObject(
    //           "INSERT INTO movies (title, genre, price) VALUES (?, ?, ?) RETURNING id",
    //           Long.class, movie.getTitle(), movie.getGenre(), movie.getPrice());
    //   Setati id-ul pe film (movie.setId(id)) si returnati filmul.
    public Movie save(Movie movie) {
        throw new UnsupportedOperationException("TODO 3.1: implementati save");
    }

    // TODO 3.2: UPDATE movies SET title = ?, genre = ?, price = ? WHERE id = ?
    //   Folositi jdbcTemplate.update(sql, ...) si returnati ce intoarce: numarul de randuri modificate.
    //   Atentie la ordinea parametrilor: id-ul este ultimul!
    public int update(Long id, Movie movie) {
        throw new UnsupportedOperationException("TODO 3.2: implementati update");
    }

    // TODO 3.3: DELETE FROM movies WHERE id = ?   (tot cu jdbcTemplate.update)
    public int deleteById(Long id) {
        throw new UnsupportedOperationException("TODO 3.3: implementati deleteById");
    }
}
