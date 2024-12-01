package org.tyresemv.smkonnect.models;

import org.tyresemv.smkonnect.database.DbConnect;
import org.tyresemv.smkonnect.database.DbOperation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SocialMediaFactory {
    private static final Map<String, SocialMediaIntegration> instances = new HashMap<>();

    public static SocialMediaIntegration getSocialMedia(String platform) {
        return instances.get(platform);
    }

    public static void registerSocialMedia(String platform, SocialMediaIntegration integration) {
        instances.put(platform, integration);
    }

    public static void loadTokensFromDatabase(String userId) {
        // Fetch tokens from the database
        String query = "SELECT platform, access_token, refresh_token, token_expiry FROM social_media_tokens WHERE user_id = ?";
        try (PreparedStatement stmt = DbOperation.getDbConnection().prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String platform = rs.getString("platform");
                String accessToken = rs.getString("access_token");
                String refreshToken = rs.getString("refresh_token");
                Timestamp expiry = rs.getTimestamp("token_expiry");

                // Initialize the respective platform instance
                SocialMediaIntegration instance = createInstanceForPlatform(platform, accessToken, refreshToken, expiry);
                if (instance != null) {
                    registerSocialMedia(platform.toLowerCase(), instance);
                }
            }
        } catch (SQLException | IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error loading tokens from database: " + e.getMessage(), e);
        }
    }

    private static SocialMediaIntegration createInstanceForPlatform(String platform, String accessToken, String refreshToken, Timestamp expiry) throws IOException, ExecutionException, InterruptedException {
        switch (platform.toLowerCase()) {
            case "twitter":
                TwitterConnect twitter = new TwitterConnect("9fDI7zKT56J7Sw5TxJmqhVQf0", "BZvuPcLqFRzEuD9FTPl3NcEp84XqbYuQFUNFOhe2iQZt2DlZx3");
                twitter.setAccessToken(accessToken);
                return twitter;

            // Add cases for other platforms like Facebook, Instagram, etc.
            default:
                return null;
        }
    }
}
