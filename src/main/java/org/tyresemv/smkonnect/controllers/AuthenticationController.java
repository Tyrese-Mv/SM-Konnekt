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
import org.tyresemv.smkonnect.database.DbOperation;
import org.tyresemv.smkonnect.models.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Objects;

public class AuthenticationController {

    public DbOperation dbOperation = new DbOperation();
    public Label validation;
    public TextField surname;
    public TextField cell_number;
    public PasswordField password_confirm;
    public PasswordField password;
    public TextField name;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button login, create_account, to_create_account;

    @FXML
    private TextField login_email, email, email_confirm;

    @FXML
    private PasswordField login_password, create_password, create_password_confirm;

    public AuthenticationController() throws SQLException {
    }

    public void login(ActionEvent event) throws IOException {
        if(dbOperation.isPasswordValid(login_email.getText(), login_password.getText())){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/tyresemv/smkonnect/Twitter/TwitterPage.fxml")));
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

    public void toCreateAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/tyresemv/smkonnect/Authentication/create_account.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void create_account(ActionEvent event) throws IOException {
        boolean account_created = dbOperation.CreateAccount(new User("123", name.getText(), surname.getText(), email.getText(), cell_number.getText(), false));
        if (account_created){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/tyresemv/smkonnect/Authentication/login.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            name.clear();
            surname.clear();
            email.clear();
            email_confirm.clear();
            cell_number.clear();
            create_password.clear();
            create_password_confirm.clear();
            validation.setText("Account already created, try again!!");
        }

    }
}
