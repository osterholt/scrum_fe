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

    @FXML
    private Label welcomeLabel;

    private boolean setWelcomeText(String firstName, String lastName) {
        if(firstName != null && lastName != null) {
            welcomeText.setText("Welcome: " + firstName + " " + lastName);
            welcomeLabel.setText("Welcome: " + firstName + " " + lastName);
        }
        return firstName != null && lastName != null;
    }

    @FXML
    void displayBoards(MouseEvent event) {
        companyChoices.show();
        System.out.println("Display Boards Pressed");
        // System.out.println(companyChoices);
    }

    @FXML
    void displayCompanies(ContextMenuEvent event) {
        System.out.println("Display Companies Pressed");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //FIXME: THis is all in the wrong method.
        User activeUser = AppFacade.getInstance().getActiveUser();
        setWelcomeText(activeUser.getFirstName(), activeUser.getLastName());
        setDropdown();
    }
    private void setDropdown() {
        double x = welcomeLabel.getLayoutX();
        x += (welcomeLabel.getWidth() + DROPDOWN_X_OFFSET); //FIXME: width is set to default 0??
        double y = welcomeLabel.getLayoutY();
        y += (welcomeLabel.getHeight() / 2);

        System.out.println("DEBUG: companyChoices set location: (" + x + ", " + y + ")");
        companyChoices.setLayoutX(x);
        companyChoices.setLayoutY(y);
        System.out.println("DEBUG: companyChoices measured location: (" + companyChoices.getLayoutX() + ", " + companyChoices.getLayoutY() + ")");

        // Add initial items to the ChoiceBox
        ObservableList<String> items = FXCollections.observableArrayList();
        ArrayList<Company> companies = AppFacade.getInstance().getActiveUser().getCompanies();
        for(Company company : companies) {
            items.add(company.getName());
        }
        items.add("DEBUG: DEFAULT");
        companyChoices.setItems(items);
    }

}
