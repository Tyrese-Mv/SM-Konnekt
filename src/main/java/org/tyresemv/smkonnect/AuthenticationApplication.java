package org.tyresemv.smkonnect;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthenticationApplication extends Application {
    private static HostServices hostServices;

    public static void main(String[] args) {
        launch(args);
    }

    public static HostServices getAppHostServices() {
        return hostServices;
    }

    @Override
    public void init() {
        // Initialize HostServices before the start method
        hostServices = getHostServices();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AuthenticationApplication.class.getResource("Authentication/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}