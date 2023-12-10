package controllers;

import model.*;
import scrum_fe.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
    }

    //TODO: Cam fix
    private void displayCompanyBoards() {
        Company company = AppFacade.getInstance().getActiveCompany();
        int i = 0;
        int j = 0;
        for(Board board : company.getBoards()) {
            // Create a Rectangle (box) and set its properties
            // Rectangle box = new Rectangle(100, 50); // width, height
            Rectangle box = new Rectangle(270, 243.333); // width, height
            box.setFill(Paint.valueOf("DA9055")); //FIXME: Color to change box

            // Create a Label (text)
            Label labelText = new Label(board.getTitle());
            GridPane.setHalignment(labelText, HPos.CENTER);
            GridPane.setValignment(labelText, VPos.TOP);
            labelText.setTranslateY(40);
            labelText.setStyle("-fx-font-size: 20px;");

            Button button = new Button("View Board");
            // button.setPadding(new Insets(10, 10, 20, 10));
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(button, VPos.BOTTOM);
            button.setTranslateY(-25);
            button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppFacade.getInstance().setActiveBoard(board.getTitle());
                try {
                    App.setRoot("columns");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

            gridPane.add(box, i, j);
            gridPane.add(labelText, i, j);
            gridPane.add(button, i, j);
            if(i > 4) {
                i = 0;
                j++;
            }
            else    
                i++;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //     // TODO Auto-generated method stub
    //     //FIXME: THis is all in the wrong method.
        User activeUser = AppFacade.getInstance().getActiveUser();
        setWelcomeText(activeUser.getFirstName(), activeUser.getLastName());
       setDropdown();
     }

    private void setDropdown() {
        ObservableList<String> items = FXCollections.observableArrayList();
        ArrayList<Company> companies = AppFacade.getInstance().getActiveUser().getCompanies();
        System.out.println("DEBUG: activeUser.getCompanies:");
        for(Company company : companies) {
            System.out.println(company.getName());
            items.add(company.getName());
        }
        
        //REMOVE following lines if ignoring scenario
        Company scenario = new Company("Code Mission Possible");
        CompanyManager.getInstance().addCompany(scenario);
        items.add(scenario.getName());
        String[] boards = {"Electric Missiles", "Soap Free Washers", "Air Computers"};
        for(String boardStr : boards) 
            scenario.addBoard(new Board(boardStr, false));

        companyChoices.setItems(items);
        companyChoices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String companyStr = items.get(newValue.intValue());
                System.out.println("Choicebox Selection: " + companyStr);
                AppFacade.getInstance().setActiveCompany(companyStr);
                System.out.println("DEBUG: ACTIVE COMPANY: " + AppFacade.getInstance().getActiveCompany());
                
                //DISPLAY BOARDS
                displayCompanyBoards();
            }
        });
        companyChoices.onActionProperty();
    }


}