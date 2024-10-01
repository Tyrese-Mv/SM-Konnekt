package org.tyresemv.smkonnect.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tyresemv.smkonnect.TwitterApplication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class AuthenticationController {
    public Label validation;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button login, create_account;

    @FXML
    private TextField login_email, create_email, create_email_confirm;

    @FXML
    private PasswordField login_password, create_password, create_password_confirm;

    public void login(ActionEvent event) throws IOException {
        if(login_email.getText().equalsIgnoreCase("123")
                && login_password.getText().equalsIgnoreCase("123")){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/tyresemv/smkonnect/Twitter/TwitterPage.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            login_email.clear();
            login_password.clear();
            validation.setText("Wrong password try again!!");
        }
//        } catch (RuntimeException e){
//            login_email.clear();
//            login_password.clear();
//            validation.setText("Wrong password try again!!");
//        }

    }
}
