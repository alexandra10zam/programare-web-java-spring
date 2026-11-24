-- Cinema FMI: datele initiale. Rulati scriptul DUPA schema.sql.
-- Daca rulati din nou schema.sql, rulati si acest script.

INSERT INTO movies (title, genre, price) VALUES
    ('Dune', 'Sci-Fi', 35),
    ('Oppenheimer', 'Drama', 32),
    ('Barbie', 'Comedy', 30),
    ('Interstellar', 'Sci-Fi', 28),
    ('Inside Out 2', 'Animation', 25),
    ('The Batman', 'Action', 33);

INSERT INTO screenings (movie_id, start_time, room, seats_left) VALUES
    (1, '18:00', 'Sala 1', 40),
    (1, '21:00', 'Sala 2', 12),
    (2, '19:30', 'Sala 1', 0),
    (4, '17:00', 'Sala 3', 25),
    (5, '16:00', 'Sala 2', 30);

SELECT * FROM movies ORDER BY id;
