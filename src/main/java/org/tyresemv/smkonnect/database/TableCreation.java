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
        CreateSocialMediaTokensTable();
    }

    @Override
    public void CreateUserTable() {
        try(final Statement stmt = this.db.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS User(
                        user_id TEXT PRIMARY KEY,
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
                    CREATE TABLE IF NOT EXISTS sessions (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          user_id TEXT NOT NULL,
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
                    CREATE TABLE IF NOT EXISTS user_credentials (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            user_id TEXT NOT NULL,
                            password_hash TEXT NOT NULL,
                            created_at TIMESTAMP NOT NULL,
                            expire_at TIMESTAMP NOT NULL,
                            FOREIGN KEY(user_id) REFERENCES users(id)
                        );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void CreateSocialMediaTokensTable() {
        try (final Statement stmt = this.db.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS social_media_tokens (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id TEXT NOT NULL,
                        platform TEXT NOT NULL,
                        access_token TEXT NOT NULL,
                        refresh_token TEXT NOT NULL,
                        token_expiry TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        UNIQUE(user_id, platform)
                    );
                    
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
