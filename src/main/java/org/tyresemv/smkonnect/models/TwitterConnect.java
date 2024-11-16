package org.tyresemv.smkonnect.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class TwitterConnect implements SocialMediaIntegration{
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
        // Placeholder for scheduling logic
        System.out.println("Scheduled post to Twitter: " + content + " at " + time);
    }
//    public static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
//    public OAuth10aService service;
//
//    private OAuth1RequestToken requestToken;
//
//    private String Verifier;
//
//    private OAuth1AccessToken accessToken;
//
//    public TwitterConnect(){
//    }
//
//    public void setService(){
//        this.service = new ServiceBuilder("9fDI7zKT56J7Sw5TxJmqhVQf0")
//                .apiSecret("BZvuPcLqFRzEuD9FTPl3NcEp84XqbYuQFUNFOhe2iQZt2DlZx3")
//                .debug()
//                .build(TwitterApi.instance());
//    }
//
//    public void ObtainRequest() throws IOException, ExecutionException, InterruptedException {
//        this.requestToken = this.service.getRequestToken();
//    }
//
//
//    public String RedirectLink(){
//        return this.service.getAuthorizationUrl(this.requestToken);
//    }
//
//    public void setVerifier(String verify){
//        this.Verifier = verify;
//    }
//
//    public void setAccessToken() throws IOException, ExecutionException, InterruptedException {
//        this.accessToken = this.service.getAccessToken(this.requestToken,this.Verifier);
//    }
//
//    public JsonNode request(String url) throws IOException, ExecutionException, InterruptedException {
//        this.setAccessToken();
//        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
//        this.service.signRequest(this.accessToken, request);
//        try (Response response = this.service.execute(request)) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readTree(response.getBody());
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
}
