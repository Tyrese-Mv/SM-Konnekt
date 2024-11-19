package org.tyresemv.smkonnect.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

public class TwitterConnect implements SocialMediaIntegration {
    private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
    private final OAuth10aService service;
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

    public TwitterConnect(String apiKey, String apiSecret) {
        this.service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .build(TwitterApi.instance());
    }

    @Override
    public void authenticate() throws IOException, ExecutionException, InterruptedException {
        this.requestToken = service.getRequestToken();
    }

    @Override
    public String getAuthorizationUrl() {
        return service.getAuthorizationUrl(requestToken);
    }

    @Override
    public void setAccessToken(String verifier) throws IOException, ExecutionException, InterruptedException {
        this.accessToken = service.getAccessToken(requestToken, verifier);
    }
    @Override
    public String getAccessToken() {
        return accessToken.getToken();
    }
    @Override
    public String getRefreshToken() {
        return "abc"; // Twitter doesn't use refresh tokens in OAuth 1.0a
    }

    public Timestamp getTokenExpiry() {
        return new Timestamp(5); // Tokens typically don't expire, but can be invalidated
    }

    @Override
    public JsonNode fetchResource(String url) throws IOException, ExecutionException, InterruptedException {
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        try (Response response = service.execute(request)) {
            return new ObjectMapper().readTree(response.getBody());
        }
    }

    @Override
    public void postUpdate(String content) throws IOException, ExecutionException, InterruptedException {
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/statuses/update.json");
        request.addParameter("status", content);
        service.signRequest(accessToken, request);
        service.execute(request);
    }

    @Override
    public void schedulePost(String content, String time) {
        System.out.println("Scheduled post to Twitter: " + content + " at " + time);
    }
}
