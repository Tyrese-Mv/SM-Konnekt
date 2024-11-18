package org.tyresemv.smkonnect.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface SocialMediaIntegration {
    void authenticate() throws IOException, ExecutionException, InterruptedException;
    String getAuthorizationUrl();
    void setAccessToken(String verifier) throws IOException, ExecutionException, InterruptedException;
    JsonNode fetchResource(String url) throws IOException, ExecutionException, InterruptedException;
    void postUpdate(String content) throws IOException, ExecutionException, InterruptedException;
    void schedulePost(String content, String time);
}