package org.tyresemv.smkonnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TwitterApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Twitter/TwitterPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        TwitterController twitterController = fxmlLoader.getController();
        twitterController.setHostServices(getHostServices());
        stage.setTitle("Twitter!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
