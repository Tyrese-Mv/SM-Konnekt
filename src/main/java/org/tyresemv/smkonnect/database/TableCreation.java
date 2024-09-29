package org.tyresemv.smkonnect.database;

import org.tyresemv.smkonnect.database.idatabase.ITableCreation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation implements ITableCreation {

    private final Connection db;

    public TableCreation() throws SQLException {
        this.db = DbConnect.getInstance().getDbConnection();
        CreateUserTable();
        CreateCredentialsTable();
        CreateSessionTable();
    }

    @Override
    public void CreateUserTable() {
        try(final Statement stmt = this.db.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS User(
                        user_id INTEGER PRIMARY KEY AUTO INCREMENT,
                        Name TEXT NOT NULL,
                        Surname TEXT NOT NULL,
                        email_address TEXT NOT NULL,
                        cellphone_number TEXT,
                        confirmed_email INTEGER DEFAULT 0
                    );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateSessionTable() {
        try(final Statement stmt = this.db.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE sessions (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          user_id INTEGER NOT NULL,
                          session_token TEXT NOT NULL,
                          expiration_time TIMESTAMP NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          FOREIGN KEY(user_id) REFERENCES users(id)
                      );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateCredentialsTable() {
        try(final Statement stmt = this.db.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE user_credentials (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            user_id INTEGER NOT NULL,
                            password_hash TEXT NOT NULL,
                            password_salt TEXT,
                            created_at TIMESTAMP NOT NULL,
                            expire_at TIMESTAMP NOT NULL,
                            FOREIGN KEY(user_id) REFERENCES users(id)
                        );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
