package org.tyresemv.smkonnect.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void navigateHome() {
        loadView("/org/tyresemv/smkonnect/Dashboard/home.fxml");
    }

    @FXML
    private void navigateSocialAccounts() {
        loadView("/org/tyresemv/smkonnect/Dashboard/social_media_accounts.fxml");
    }

    @FXML
    private void navigateCreatePost() {
        loadView("/org/tyresemv/smkonnect/Dashboard/create_post.fxml");
    }

    @FXML
    private void navigateNotifications() {
        loadView("/org/tyresemv/smkonnect/views/notifications.fxml");
    }

    @FXML
    private void navigateSettings() {
        loadView("/org/tyresemv/smkonnect/views/settings.fxml");
    }

    @FXML
    private void logout() {
        // Handle logout logic
        Alert logoutAlert = new Alert(Alert.AlertType.INFORMATION, "You have been logged out.");
        logoutAlert.show();
        System.exit(0);
    }

    private void loadView(String fxmlPath) {
        try {
            Parent newView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            contentArea.getChildren().setAll(newView);
        } catch (IOException e) {
            showError("Failed to load view: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText("Error");
        alert.show();
    }
}
