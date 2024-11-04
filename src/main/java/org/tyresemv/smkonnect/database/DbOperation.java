package org.tyresemv.smkonnect.database;

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

    public boolean isPasswordValid(String Password, String Username){
        try{
            PreparedStatement login = this.db.prepareStatement("SELECT password_hash FROM user_credentials WHERE user_id = ?;");
            login.setString(1, Username);
            ResultSet result = login.executeQuery();
            while(result.next()){
                if (result.getString("password_hash").equals(Password)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean CreateAccount(User User){
        try{
            PreparedStatement newUser = this.db.prepareStatement("INSERT INTO User(user_id, Name, Surname, email_address, cellphone_number, confirmed_email) VALUES(?, ?, ?, ?, ?, ?); ");
            newUser.setString(1, User.userID());
            newUser.setString(2, User.Name());
            newUser.setString(3, User.Surname());
            newUser.setString(4, User.email_address());
            newUser.setString(5, User.cellphone_number());
            newUser.setString(6, String.valueOf(User.confirmed_email()));
            newUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }




}
