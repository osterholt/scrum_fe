package controllers;
import model.*;
import scrum_fe.App;

import java.io.IOException;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {

    private final String SPACE = " ";

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private TextField password;

    @FXML
    private Button signUp;

    @FXML
    void signUp(ActionEvent event) throws IOException {
        String[] fullNameArr = fullName.getText().split(SPACE);
        String emailStr = email.getText();
        String passwordStr = password.getText();

        if(LoginManager.getInstance().checkEmail(emailStr) && LoginManager.getInstance().checkPassword(passwordStr) && fullNameArr.length > 1) {
            UUID user = AppFacade.getInstance().signUp(fullNameArr[0], fullNameArr[fullNameArr.length - 1], emailStr, passwordStr);
            if(user != null) {
                System.out.println("DEBUG: Sign Up Success!!");
                System.out.println("Active User: " + AppFacade.getInstance().getActiveUser());
                // App.setRoot("");
            }
            else {
                //TODO: Throw error message.
            }
        }
        else {
            //TODO: Throw error message.
        }
    }

}
