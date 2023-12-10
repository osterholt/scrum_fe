package controllers;
import model.*;
import scrum_fe.App;

import java.io.IOException;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button backToWelcome;

    @FXML
    private Label errorLabelInvalid; 

    @FXML
    private Label errorLabelAlreadyExists;

    @FXML
    void backToWelcome(ActionEvent event) throws IOException {
        errorLabelInvalid.setVisible(false);
        errorLabelAlreadyExists.setVisible(false);
        System.out.println("DEBUG: Back button pressed!");
        App.setRoot("welcome");
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        String[] fullNameArr = fullName.getText().split(SPACE);
        String emailStr = email.getText();
        //System.out.println(emailStr);
        String passwordStr = password.getText();

        if(LoginManager.getInstance().checkEmail(emailStr) && LoginManager.getInstance().checkPassword(passwordStr) && fullNameArr.length > 1) {
            UUID userID = AppFacade.getInstance().signUp(fullNameArr[0], fullNameArr[fullNameArr.length - 1], emailStr, passwordStr);
            User user = LoginManager.getInstance().getUser(userID);
            DataWriter.saveUsers();
            //Go to Board Display
            System.out.println("DEBUG: Sign Up Success!!");
            System.out.println("Active User: " + AppFacade.getInstance().getActiveUser());
            // BoardController.setWelcomeText(user.getFirstName(), user.getLastName());
            //Set Companies!
            App.setRoot("columns");
            
        }
        else {
            if(LoginManager.getInstance().getUser(emailStr) != null) {
                // User is null, show the error label
            System.out.println("DEBUG: Sign Up Failed, Already Exists!!");
            errorLabelInvalid.setVisible(false);
            errorLabelAlreadyExists.setVisible(true);
            }
            else {
                // User is null, show the error label
                System.out.println("DEBUG: Sign Up Failed, Invalid!!");
                errorLabelAlreadyExists.setVisible(false);
                errorLabelInvalid.setVisible(true);

            }
        }
    }

}
