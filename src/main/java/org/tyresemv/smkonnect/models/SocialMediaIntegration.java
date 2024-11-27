package org.tyresemv.smkonnect.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

public interface SocialMediaIntegration {
    void authenticate() throws IOException, ExecutionException, InterruptedException;
    String getAuthorizationUrl();
    void setAccessToken(String verifier) throws IOException, ExecutionException, InterruptedException;
    JsonNode fetchResource(String url) throws IOException, ExecutionException, InterruptedException;
    boolean postUpdate(String content) throws IOException, ExecutionException, InterruptedException;
    void schedulePost(String content, String time);
    String getAccessToken();

    String getRefreshToken();

    Timestamp getTokenExpiry();
}
