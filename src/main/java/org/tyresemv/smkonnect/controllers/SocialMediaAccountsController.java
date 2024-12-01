package org.tyresemv.smkonnect.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tyresemv.smkonnect.AuthenticationApplication;
import org.tyresemv.smkonnect.database.DbOperation;
import org.tyresemv.smkonnect.database.TokenRepository;
import org.tyresemv.smkonnect.models.SocialMediaFactory;
import org.tyresemv.smkonnect.models.SocialMediaIntegration;
import org.tyresemv.smkonnect.models.TwitterConnect;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SocialMediaAccountsController {

    @FXML
    private ListView<String> accountsListView;

    private ObservableList<String> accounts;

    @FXML
    public void initialize() {
        refreshAccounts();
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
                SocialMediaIntegration integration = getOrCreateIntegration(platform);

                if (integration != null) {
                    integration.authenticate();

                    String authUrl = integration.getAuthorizationUrl();
                    AuthenticationApplication.getAppHostServices().showDocument(authUrl);

                    handleOAuthCompletion(platform, integration);
                } else {
                    showError("Platform not supported: " + platform);
                }
            } catch (Exception e) {
                showError("Failed to add account: " + e.getMessage());
            }
        });
    }

    @FXML
    private void removeAccount() {
        String selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) {
            showWarning("No account selected", "Please select an account to remove.");
            return;
        }

        try {
            TokenRepository tokenRepo = TokenRepository.getInstance();
//            tokenRepo.deleteToken("user123", selectedAccount.split(" ")[0]); // Adjust to actual user ID and format
            accounts.remove(selectedAccount);
            showInfo("Account Removed", selectedAccount + " has been removed.");
        } catch (Exception e) {
            showError("Failed to remove account: " + e.getMessage());
        }
    }

    @FXML
    private void refreshAccounts() {
        List<String> dbAccounts = DbOperation.initAccounts();
        if (dbAccounts != null) {
            accounts = FXCollections.observableArrayList(dbAccounts);
            accountsListView.setItems(accounts);
        } else {
            showWarning("No Accounts", "No accounts found in the database.");
        }
    }

    private void handleOAuthCompletion(String platform, SocialMediaIntegration integration) {
        TextInputDialog verifierDialog = new TextInputDialog();
        verifierDialog.setTitle("Verification Code");
        verifierDialog.setHeaderText("Enter the verification code you received:");
        verifierDialog.setContentText("Code:");

        verifierDialog.showAndWait().ifPresent(verifier -> {
            try {
                integration.setAccessToken(verifier);

                TokenRepository tokenRepo = TokenRepository.getInstance();
                tokenRepo.saveToken(
                        "user123", // Replace with actual user ID
                        platform,
                        integration.getAccessToken(),
                        integration.getRefreshToken(),
                        integration.getTokenExpiry()
                );

                accounts.add(platform + " (Active)");
                showInfo("Account Added", platform + " account added successfully!");
            } catch (Exception e) {
                showError("Failed to complete OAuth: " + e.getMessage());
            }
        });
    }

    private SocialMediaIntegration getOrCreateIntegration(String platform) {
        SocialMediaIntegration integration = SocialMediaFactory.getSocialMedia(platform.toLowerCase());
        if (integration == null) {
            switch (platform.toLowerCase()) {
                case "twitter":
                    integration = new TwitterConnect("9fDI7zKT56J7Sw5TxJmqhVQf0", "BZvuPcLqFRzEuD9FTPl3NcEp84XqbYuQFUNFOhe2iQZt2DlZx3");
                    break;
                case "instagram":
                    // Add Instagram integration
                    break;
                case "facebook":
                    // Add Facebook integration
                    break;
            }
            if (integration != null) {
                SocialMediaFactory.registerSocialMedia(platform.toLowerCase(), integration);
            }
        }
        return integration;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText("Error");
        alert.show();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setHeaderText(title);
        alert.show();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(title);
        alert.show();
    }
}