package controllers;

import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BoardController implements Initializable {
    private final double DROPDOWN_X_OFFSET = 10.0;

    @FXML
    private ChoiceBox<String> companyChoices;
    // Consider replacing with title pane

    @FXML
    private Text welcomeText;

    @FXML
    private GridPane gridPane;

    private boolean setWelcomeText(String firstName, String lastName) {
        if(firstName != null && lastName != null) {
            welcomeText.setText("Welcome: " + firstName + " " + lastName);
        }
        return firstName != null && lastName != null;
    }

    @FXML
    void displayBoards(MouseEvent event) {
        companyChoices.show();
        System.out.println("Display Boards Pressed");
        // System.out.println(companyChoices);
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
        // double x = welcomeLabel.getLayoutX();
        // x += (welcomeLabel.getWidth() + DROPDOWN_X_OFFSET); //FIXME: width is set to default 0??
        // double y = welcomeLabel.getLayoutY();
        // y += (welcomeLabel.getHeight() / 2);

        // System.out.println("DEBUG: companyChoices set location: (" + x + ", " + y + ")");
        // companyChoices.setLayoutX(x);
        // companyChoices.setLayoutY(y);
        // System.out.println("DEBUG: companyChoices measured location: (" + companyChoices.getLayoutX() + ", " + companyChoices.getLayoutY() + ")");

        // Add initial items to the ChoiceBox
        ObservableList<String> items = FXCollections.observableArrayList();
        ArrayList<Company> companies = AppFacade.getInstance().getActiveUser().getCompanies();
        System.out.println("DEBUG: activeUser.getCompanies:");
        for(Company company : companies) {
            System.out.println(company.getName());
            items.add(company.getName());
        }
        items.add("DEBUG: DEFAULT");
        companyChoices.setItems(items);
        companyChoices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String companyStr = items.get(newValue.intValue());
                System.out.println("Choicebox Selection: " + companyStr);
                AppFacade.getInstance().setActiveCompany(companyStr);
                System.out.println("DEBUG: ACTIVE COMPANY: " + AppFacade.getInstance().getActiveCompany());
            }
        });
        companyChoices.onActionProperty();
    }

}