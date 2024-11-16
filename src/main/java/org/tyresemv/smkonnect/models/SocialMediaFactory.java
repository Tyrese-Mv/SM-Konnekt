package org.tyresemv.smkonnect.models;

public class SocialMediaFactory {
    public static SocialMediaIntegration getSocialMedia(String platform) {
        return switch (platform.toLowerCase()) {
            case "twitter" -> new TwitterConnect("9fDI7zKT56J7Sw5TxJmqhVQf0", "BZvuPcLqFRzEuD9FTPl3NcEp84XqbYuQFUNFOhe2iQZt2DlZx3");
//            case "facebook" -> new FacebookConnect("APP_ID", "APP_SECRET");
//            case "instagram" -> new InstagramConnect("CLIENT_ID", "CLIENT_SECRET");
            default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
        };
    }
}
