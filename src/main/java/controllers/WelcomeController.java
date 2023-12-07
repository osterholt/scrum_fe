package controllers;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import scrum_fe.App;

public class WelcomeController implements Initializable{

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // init();
    }

    private void init() {
        String[] userInfo = {"Atticus", "Madden", "amadden@gmail.com", "password"};
        AppFacade.getInstance().signUp(userInfo[0], userInfo[1], userInfo[2], userInfo[3]);
        Company company = new Company("Code Mission Possible");
        CompanyManager.getInstance().addCompany(company);
        
        // Three projects: Electric Missiles, Soap Free Washers, and Air Computers
        String[] boards = {"Electric Missiles", "Soap Free Washers", "Air Computers"};
        for(String boardStr : boards) 
            company.addBoard(new Board(boardStr, false));

        AppFacade.getInstance().removeActive();
    }

}
