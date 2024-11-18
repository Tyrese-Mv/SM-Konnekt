package org.tyresemv.smkonnect.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {
    private Stage stage;
    private Scene scene;

    Label iconLabel = new Label("\uf015"); // Home icon


    @FXML
    public void navigateHome(ActionEvent event) {
        System.out.println("Navigating to Home (Already on Dashboard)");
        iconLabel.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 20px;");
    }

    @FXML
    public void navigateSocialAccounts(ActionEvent event) throws IOException {
        navigateTo(event, "/org/tyresemv/smkonnect/Dashboard/social_media_accounts.fxml");
    }

    @FXML
    public void navigateCreatePost(ActionEvent event) throws IOException {
        navigateTo(event, "/org/tyresemv/smkonnect/CreatePostPage.fxml");
    }

    @FXML
    public void navigateNotifications(ActionEvent event) throws IOException {
        navigateTo(event, "/org/tyresemv/smkonnect/NotificationsPage.fxml");
    }

    @FXML
    public void navigateSettings(ActionEvent event) throws IOException {
        navigateTo(event, "/org/tyresemv/smkonnect/SettingsPage.fxml");
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        navigateTo(event, "/org/tyresemv/smkonnect/Authentication/login.fxml");
    }

    private void navigateTo(ActionEvent event, String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourcePath)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
