package ro.example.lab7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

// Exercitiul 1 - JDBC "de mana", fara ajutorul Spring.
// Ruleaza o singura data la pornire, doar cu profilul jdbc-demo (configuratia "Lab7 - JDBC demo").
@Component
@Profile("jdbc-demo")
public class JdbcDemoRunner implements CommandLineRunner {

    // DataSource-ul este creat de Spring Boot din application.properties.
    private final DataSource dataSource;

    public JdbcDemoRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        String genre = "Sci-Fi";
        // O valoare trimisa de un utilizator rau-intentionat, de exemplu prin ?genre=... in URL.
        String attack = "x' OR '1'='1";

        System.out.println();
        System.out.println("===== 1. JDBC cu PreparedStatement =====");

        // TODO 1.1: Intr-un try-with-resources, obtineti o conexiune: dataSource.getConnection()
        //   si afisati clasa ei: System.out.println("Connection: " + connection.getClass().getName());

        // TODO 1.2: In acelasi try, creati un PreparedStatement pentru query-ul:
        //   SELECT id, title, genre, price FROM movies WHERE genre = ?

        // TODO 1.3: Legati parametrul: statement.setString(1, genre);   (indicii incep de la 1!)
        //   Executati query-ul cu statement.executeQuery() si obtineti un ResultSet, tot cu try-with-resources.

        // TODO 1.4: Parcurgeti rezultatele cu while (resultSet.next()) si afisati, pentru fiecare film,
        //   resultSet.getLong("id"), resultSet.getString("title") si resultSet.getDouble("price").
        //   Daca nu exista niciun rand, afisati "Niciun film.".

        // TODO 1.5: Dupa ce ruleaza, inlocuiti genre cu attack in setString si rulati din nou.
        //   Cate filme intoarce acum? Comparati cu partea a 2-a de mai jos.

        System.out.println();
        System.out.println("===== 2. SQL injection cu Statement si concatenare (NU faceti asa!) =====");
        String unsafeSql = "SELECT id, title, genre FROM movies WHERE genre = '" + attack + "'";
        System.out.println("Query-ul trimis: " + unsafeSql);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(unsafeSql)) {
            while (resultSet.next()) {
                System.out.println("  " + resultSet.getString("title") + " (" + resultSet.getString("genre") + ")");
            }
        }
    }
}
