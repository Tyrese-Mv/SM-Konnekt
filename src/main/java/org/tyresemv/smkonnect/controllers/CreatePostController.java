package org.tyresemv.smkonnect.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.tyresemv.smkonnect.models.SocialMediaFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CreatePostController {
    @FXML
    private TextArea postTextArea;

    @FXML
    private ComboBox<String> accountsComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        // Load social media accounts into the ComboBox
        accountsComboBox.getItems().addAll("Twitter", "Facebook", "Instagram");
    }

    @FXML
    private void postNow() throws IOException, ExecutionException, InterruptedException {
        String postContent = postTextArea.getText();
        String selectedAccount = accountsComboBox.getValue();

        if (postContent.isEmpty() || selectedAccount == null) {
            showError("Please enter post content and select an account.");
            return;
        }

        // TODO: Call backend to post immediately
        boolean success = SocialMediaFactory.getSocialMedia(selectedAccount.toLowerCase())
                .postUpdate(postContent); // Adjust this to match your method signature.

        // We get a new instance from the Factory to make a new post, the issue is the new object hasn't done the oauth flow meaning when we want to make a post, we will get an error because "we haven't authenticated"

        if (success) {
            showSuccess("Post published successfully.");
        } else {
            showError("Failed to publish post. Please try again.");
        }
    }

    @FXML
    private void schedulePost() {
        String postContent = postTextArea.getText();
        String selectedAccount = accountsComboBox.getValue();

        if (postContent.isEmpty() || selectedAccount == null) {
            showError("Please enter post content and select an account.");
            return;
        }

        // TODO: Add scheduling logic here
        showSuccess("Post scheduled successfully."); // Placeholder
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void showSuccess(String message) {
        // Implement success dialog or notification
        System.out.println(message);
    }
}
