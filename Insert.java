package Cesar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;


public class Insert {
	public boolean inscrition = false;
    // Constructor
    public Insert(String mail, String password) {
        insertBdd(mail, password);
    }

    private void insertBdd(String mail, String password) {
        
        String sql = "INSERT INTO user (Mail, Password, Salt) VALUES (?, ?, ?)";

        try {
            // Obtain the connection from the Bdd class
            Connection connection = new Bdd().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Generate a random salt
            String salt = BCrypt.gensalt();
            
            // Hash the password with the generated salt
            String hashedPassword = BCrypt.hashpw(password, salt);
            
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, salt);

            // Execute the SQL INSERT statement
            int rowsInserted = preparedStatement.executeUpdate();
            
            if (rowsInserted > 0) {
                inscrition = true;
            } else {
            	inscrition = false;
            }

            // Close resources (preparedStatement, connection)
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getInsert() {
        return this.inscrition;
    }
}
