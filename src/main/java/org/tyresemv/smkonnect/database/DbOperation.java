package org.tyresemv.smkonnect.database;

import org.mindrot.jbcrypt.BCrypt;
import org.tyresemv.smkonnect.models.User;

import java.sql.*;
import java.util.ArrayList;

public class DbOperation {

    private static Connection db;

    public DbOperation() throws SQLException {
        new TableCreation();
        db = DbConnect.getInstance().getDbConnection();
    }

    public static Connection getDbConnection(){
        return db;
    }


    public boolean isPasswordValid(String password, String username) {
        try {
            PreparedStatement login = this.db.prepareStatement("SELECT password_hash FROM user_credentials WHERE user_id = ?;");
            login.setString(1, username);
            ResultSet result = login.executeQuery();

            if (result.next()) {
                String storedHash = result.getString("password_hash");
                return BCrypt.checkpw(password, storedHash); // Compare hashed password
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean CreateAccount(User User, String hashedPassword) {
        try {
            PreparedStatement newUser = this.db.prepareStatement(
                    "INSERT INTO User(user_id, Name, Surname, email_address, cellphone_number, confirmed_email) VALUES(?, ?, ?, ?, ?, ?);"
            );
            newUser.setString(1, User.userID());
            newUser.setString(2, User.Name());
            newUser.setString(3, User.Surname());
            newUser.setString(4, User.email_address());
            newUser.setString(5, User.cellphone_number());
            newUser.setBoolean(6, User.confirmed_email());
            newUser.executeUpdate();

            PreparedStatement newCredentials = this.db.prepareStatement(
                    "INSERT INTO user_credentials(user_id, password_hash, created_at, expire_at) VALUES(?, ?, datetime('now'), datetime('now', '+1 year'));"
            );
            newCredentials.setString(1, User.email_address());
            newCredentials.setString(2, hashedPassword);
            newCredentials.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> initAccounts(){
        ArrayList<String> accounts = new ArrayList<>();// Initialize the ObservableList

        try {
            // Fetch accounts from the database
            String query = "SELECT platform, token_expiry FROM social_media_tokens";
            try (PreparedStatement stmt = db.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String platform = rs.getString("platform");
                    Timestamp tokenExpiry = rs.getTimestamp("token_expiry");

                    // Determine the status of the account
                    String status;
                    if (tokenExpiry == null || tokenExpiry.before(new Timestamp(System.currentTimeMillis()))) {
                        status = "Needs Re-authentication";
                    } else {
                        status = "Active";
                    }

                    // Add the account with its status to the list
                    accounts.add(platform + " (" + status + ")");
                }
                return accounts;
            }
        } catch (SQLException e) {
            // Handle database errors
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load accounts: " + e.getMessage());
//            alert.setHeaderText("Database Error");
//            alert.show();
            return null;
        }
    }


}
