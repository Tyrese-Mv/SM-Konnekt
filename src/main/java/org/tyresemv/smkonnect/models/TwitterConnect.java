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

public class TwitterConnect {
    public static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
    public OAuth10aService service;

    private OAuth1RequestToken requestToken;

    private String Verifier;

    private OAuth1AccessToken accessToken;

    public TwitterConnect(){
    }

    public void setService(){
        this.service = new ServiceBuilder("9fDI7zKT56J7Sw5TxJmqhVQf0")
                .apiSecret("BZvuPcLqFRzEuD9FTPl3NcEp84XqbYuQFUNFOhe2iQZt2DlZx3")
                .debug()
                .build(TwitterApi.instance());
    }

    public void ObtainRequest() throws IOException, ExecutionException, InterruptedException {
        this.requestToken = this.service.getRequestToken();
    }


    public String RedirectLink(){
        return this.service.getAuthorizationUrl(this.requestToken);
    }

    public void setVerifier(String verify){
        this.Verifier = verify;
    }

    public void setAccessToken() throws IOException, ExecutionException, InterruptedException {
        this.accessToken = this.service.getAccessToken(this.requestToken,this.Verifier);
    }

    public JsonNode request(String url) throws IOException, ExecutionException, InterruptedException {
        this.setAccessToken();
        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
        this.service.signRequest(this.accessToken, request);
        try (Response response = this.service.execute(request)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(response.getBody());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




//    public static void main(String... args) throws IOException, InterruptedException, ExecutionException {
//        final OAuth10aService service =
//        final Scanner in = new Scanner(System.in);
//
//        System.out.println("=== Twitter's OAuth Workflow ===");
//        System.out.println();
//
//        // Obtain the Request Token
//        System.out.println("Fetching the Request Token...");
//        final OAuth1RequestToken requestToken = service.getRequestToken();
//        System.out.println("Got the Request Token!");
//        System.out.println();
//
//        System.out.println("Now go and authorize ScribeJava here:");
//        System.out.println(service.getAuthorizationUrl(requestToken));
//        System.out.println("And paste the verifier here");
//        System.out.print(">>");
//        final String oauthVerifier = in.nextLine();
//        System.out.println();
//
//        // Trade the Request Token and Verifier for the Access Token
//        System.out.println("Trading the Request Token for an Access Token...");
//        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauthVerifier);
//        System.out.println("Got the Access Token!");
//        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
//        System.out.println();
//
//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//        service.signRequest(accessToken, request);
//        try (Response response = service.execute(request)) {
//            System.out.println("Got it! Lets see what we found...");
//            System.out.println();
//            System.out.println(response.getBody());
//        }
//        System.out.println();
//        System.out.println("That's it man! Go and build something awesome with ScribeJava! :)");
//    }
}
