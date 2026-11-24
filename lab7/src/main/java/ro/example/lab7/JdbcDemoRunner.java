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
        String sql = "SELECT id, title, genre, price FROM movies WHERE genre = ?";
        for (String value : List.of(genre, attack)) {
            // try-with-resources: Connection, PreparedStatement si ResultSet se inchid automat, in ordine inversa.
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                System.out.println("Connection: " + connection.getClass().getName());
                statement.setString(1, value);
                try (ResultSet resultSet = statement.executeQuery()) {
                    System.out.println("Filmele cu genul [" + value + "]:");
                    boolean found = false;
                    while (resultSet.next()) {
                        found = true;
                        System.out.println("  " + resultSet.getLong("id") + ". " + resultSet.getString("title")
                                + " - " + resultSet.getDouble("price") + " RON");
                    }
                    if (!found) {
                        System.out.println("  Niciun film.");
                    }
                }
            }
        }

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
