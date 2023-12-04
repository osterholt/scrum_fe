package controllers;

import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BoardController implements Initializable {
    private final double DROPDOWN_X_OFFSET = 10.0;

    @FXML
    private ChoiceBox<String> companyChoices;

    @FXML
    private Text welcomeText;

    private boolean setWelcomeText(String firstName, String lastName) {
        if(firstName != null && lastName != null) {
            welcomeText.setText("Welcome: " + firstName + " " + lastName);
        }
        return firstName != null && lastName != null;
    }

    @FXML
    void displayBoards(MouseEvent event) {
        
    }

    @FXML
    void displayCompanies(ContextMenuEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //FIXME: THis is all in the wrong method.
        User activeUser = AppFacade.getInstance().getActiveUser();
        setWelcomeText(activeUser.getFirstName(), activeUser.getLastName());
        // double x = welcomeText.getX();
        // double y = welcomeText.getY();
        // companyChoices.setLayoutX(x + DROPDOWN_X_OFFSET);
        // companyChoices.setLayoutY(y);

        // // Add initial items to the ChoiceBox
        // ObservableList<String> items = FXCollections.observableArrayList();
        // ArrayList<Company> companies = AppFacade.getInstance().getActiveUser().getCompanies();
        // for(Company company : companies) {
        //     items.add(company.getName());
        // }
        // companyChoices.setItems(items);
    }

}
