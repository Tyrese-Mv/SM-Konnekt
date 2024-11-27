package org.tyresemv.smkonnect.database;

import org.tyresemv.smkonnect.models.TokenData;

import java.sql.*;
import java.util.Optional;

public class TokenRepository {
    private static TokenRepository instance;
    private static Connection db;

    // Private constructor to prevent instantiation
    private TokenRepository() throws SQLException {
        db = DbConnect.getInstance().getDbConnection();
    }

    // Public method to provide access to the single instance
    public static synchronized TokenRepository getInstance() {
        if (instance == null) {
            try {
                instance = new TokenRepository();
            } catch (SQLException e) {
                throw new RuntimeException("Error initializing TokenRepository: " + e.getMessage(), e);
            }
        }
        return instance;
    }

    public static Connection getTokenDb() {
        return db;
    }

    public void saveToken(String userId, String platform, String accessToken, String refreshToken, Timestamp expiry) {
        String query = """
        INSERT INTO social_media_tokens (user_id, platform, access_token, refresh_token, token_expiry)
        VALUES (?, ?, ?, ?, ?)
        ON CONFLICT(user_id, platform) DO UPDATE
        SET access_token = excluded.access_token,
            refresh_token = excluded.refresh_token,
            token_expiry = excluded.token_expiry,
            updated_at = CURRENT_TIMESTAMP;
        """;
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, platform);
            stmt.setString(3, accessToken);
            stmt.setString(4, refreshToken);
            stmt.setTimestamp(5, expiry);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving token: " + e.getMessage(), e);
        }
    }

    public Optional<TokenData> getToken(String userId, String platform) {
        String query = "SELECT access_token, refresh_token, token_expiry FROM social_media_tokens WHERE user_id = ? AND platform = ?;";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, platform);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new TokenData(
                            rs.getString("access_token"),
                            rs.getString("refresh_token"),
                            rs.getTimestamp("token_expiry")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving token: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}
