package controllers;
import model.*;
import scrum_fe.App;

import java.io.IOException;
import java.util.UUID;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button login;

    @FXML
    void login(ActionEvent event) throws IOException {
        System.out.println("login pressed af");
        String emailStr = email.getText();
        String passwordStr = password.getText();

        // FROM CAM: Removed the checks for email and password because they already exist!
        AppFacade.getInstance().login(emailStr, passwordStr);
        User user = AppFacade.getInstance().getCurrentUser();
        //Go to Board Display
        if(user != null) {
            System.out.println("DEBUG: Log in Success!!");
            System.out.println("Active User: " + AppFacade.getInstance().getActiveUser());
            // BoardController.setWelcomeText(user.getFirstName(), user.getLastName());
            //Set Companies!
            App.setRoot("board");
        }
        
    }

}
