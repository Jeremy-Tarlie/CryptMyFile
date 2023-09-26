package Cesar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {
    private Object[][] donnees;

    // Constructor
    public Select(String mail) {
        selectBdd(mail);
    }

    private void selectBdd(String mail) {
        this.donnees = new Object[10][6];
        String sql = "SELECT * FROM user WHERE Mail = ?";

        try {
            // Obtain the connection from the Bdd class
            Connection connection = new Bdd().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mail);

            ResultSet res = preparedStatement.executeQuery();

            // Check if there are rows in the result set
            if (res.next()) {
                this.donnees[0][0] = res.getString("Mail");
                this.donnees[0][1] = res.getString("Password");
                this.donnees[0][2] = res.getString("Salt");
                this.donnees[0][3] = res.getString("ID");
            }

            // Close resources (preparedStatement, connection)
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getSelection() {
        return this.donnees;
    }
}
