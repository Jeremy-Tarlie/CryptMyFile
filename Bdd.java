package Cesar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd  {
    private String jdbcUrl = "jdbc:mysql://localhost:3306/cryptmyfile";
    private String user = "root"; // Nom d'utilisateur MySQL
    private String password = ""; // Mot de passe MySQL
    private Connection connection;

    
    public Bdd() {
        connexionBdd();
    }

    private void connexionBdd() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.jdbcUrl, this.user, this.password);

            if (this.connection != null) {
                System.out.println("La connexion à la base de données a réussi.");
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // Getter pour obtenir la connexion
    public Connection getConnection() {
        return this.connection;
    }

	
}
