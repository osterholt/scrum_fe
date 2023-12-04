package controllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import scrum_fe.App;

public class WelcomeController {

    @FXML
    private Button returnUser;

    @FXML
    private Button signUp;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        System.out.println("Login Button Pressed");
        App.setRoot("login");

    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        System.out.println("SignUp Button Pressed");
        App.setRoot("signup");
    }

}
