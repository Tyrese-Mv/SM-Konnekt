package org.tyresemv.smkonnect.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tyresemv.smkonnect.AuthenticationApplication;
import org.tyresemv.smkonnect.database.DbConnect;
import org.tyresemv.smkonnect.database.DbOperation;
import org.tyresemv.smkonnect.database.TokenRepository;
import org.tyresemv.smkonnect.models.SocialMediaFactory;
import org.tyresemv.smkonnect.models.SocialMediaIntegration;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialMediaAccountsController {

    @FXML
    private ListView<String> accountsListView;

    private ObservableList<String> accounts;

    @FXML
    public void initialize() throws SQLException {

        ArrayList<String> dbAccounts = DbOperation.initAccounts();

        if (dbAccounts == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load accounts: ");
            alert.setHeaderText("Database Error");
            alert.show();
        } else {
            accounts = FXCollections.observableArrayList(dbAccounts);
            accountsListView.setItems(accounts);
        }
    }

    @FXML
    private void addAccount() {
        List<String> platforms = Arrays.asList("Twitter", "Instagram", "Facebook");
        ChoiceDialog<String> platformDialog = new ChoiceDialog<>(platforms.get(0), platforms);
        platformDialog.setTitle("Add Social Media Account");
        platformDialog.setHeaderText("Select a platform to add:");
        platformDialog.setContentText("Platform:");

        platformDialog.showAndWait().ifPresent(platform -> {
            try {
                // Initialize OAuth flow for the selected platform
                SocialMediaIntegration integration = SocialMediaFactory.getSocialMedia(platform);
                integration.authenticate();

                // Open the authorization URL in the user's browser
                String authUrl = integration.getAuthorizationUrl();
                AuthenticationApplication.getAppHostServices().showDocument(authUrl);

                // Prompt user for verifier code
                TextInputDialog verifierDialog = new TextInputDialog();
                verifierDialog.setTitle("Enter Verification Code");
                verifierDialog.setHeaderText("Paste the verification code you received:");
                verifierDialog.setContentText("Verification Code:");

                verifierDialog.showAndWait().ifPresent(verifier -> {
                    try {
                        // Complete OAuth process
                        integration.setAccessToken(verifier);

                        // Save tokens in the database
                        TokenRepository tokenRepo = TokenRepository.getInstance();
                        tokenRepo.saveToken(
                                "user123",
                                platform,
                                integration.getAccessToken(),
                                integration.getRefreshToken(),
                                integration.getTokenExpiry()
                        );

                        // Update the UI
                        accounts.add(platform + " (Active)");

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, platform + " account added successfully!");
                        successAlert.setHeaderText("Account Added");
                        successAlert.show();
                    } catch (Exception e) {
                        showError("Failed to complete authentication for " + platform + ": " + e.getMessage());
                    }
                });

            } catch (Exception e) {
                showError("Failed to initialize authentication for " + platform + ": " + e.getMessage());
            }
        });
    }

    @FXML
    private void removeAccount() {
        String selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            accounts.remove(selectedAccount);
            // Logic to remove from the database can go here
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an account to remove.");
            alert.setHeaderText("No Account Selected");
            alert.show();
        }
    }

    @FXML
    private void refreshAccounts() {
        // Logic to refresh the account data
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Refreshing account data...");
        alert.setHeaderText("Refresh in Progress");
        alert.show();
        // Add code to re-fetch account data from the database or server
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText("Error");
        alert.show();
    }
}