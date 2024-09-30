package org.tyresemv.smkonnect.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tyresemv.smkonnect.models.TwitterConnect;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TwitterController {
    private HostServices hostServices;

    @FXML
    private Button connect, verify, link;

    @FXML
    private TextField verification;

    @FXML
    private ListView<String> AccountInfo = new ListView<>();

    private TwitterConnect twitter = new TwitterConnect();

    @FXML
    public void OnConnectClick() throws IOException, ExecutionException, InterruptedException {
        twitter.setService();
        twitter.ObtainRequest();

    }

    @FXML
    public void OnRedirectLink(){
        String redirectLink = twitter.RedirectLink();
        hostServices.showDocument(redirectLink);
//        link.setOnAction(event -> {
//
//            System.out.println("clicked inside");
//        });
//        System.out.println("clicked outside");
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void OnVerification() throws IOException, ExecutionException, InterruptedException {
        String verificationCode = verification.getText();
        twitter.setVerifier(verificationCode);

        JsonNode jsonObject =  twitter.request("https://api.twitter.com/1.1/account/verify_credentials.json");

        AccountInfo.getItems().addAll(
                jsonObject.get("name").asText(),
                jsonObject.get("screen_name").asText(),
                jsonObject.get("location").asText(),
                jsonObject.get("description").asText(),
                jsonObject.get("followers_count").asText(),
                jsonObject.get("friends_count").asText()
        );

    }

}
