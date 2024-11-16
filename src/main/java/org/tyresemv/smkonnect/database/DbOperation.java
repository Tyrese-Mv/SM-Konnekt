package org.tyresemv.smkonnect.database;

import org.mindrot.jbcrypt.BCrypt;
import org.tyresemv.smkonnect.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbOperation {

    private Connection db;

    public DbOperation() throws SQLException {
        new TableCreation();
        this.db = DbConnect.getInstance().getDbConnection();
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




}
