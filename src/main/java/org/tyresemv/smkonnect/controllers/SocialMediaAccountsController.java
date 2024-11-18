package org.tyresemv.smkonnect.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class SocialMediaAccountsController {
    @FXML
    private ListView<String> accountsListView;

    private ObservableList<String> accounts;

    @FXML
    public void initialize() {
        // Mock data for demonstration
        accounts = FXCollections.observableArrayList(
                "Twitter (Active)",
                "Instagram (Needs Re-authentication)",
                "Facebook (Active)"
        );
        accountsListView.setItems(accounts);
    }

    @FXML
    private void addAccount() {
        // Logic to add a new account (e.g., OAuth flow)
        // This is just a placeholder for now
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Add Account functionality is under development.");
        alert.setHeaderText("Feature Not Yet Implemented");
        alert.show();
    }

    @FXML
    private void removeAccount() {
        String selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            accounts.remove(selectedAccount);
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
        // This is where you'd add code to refresh account data (e.g., re-fetch data from the server)
    }
}
