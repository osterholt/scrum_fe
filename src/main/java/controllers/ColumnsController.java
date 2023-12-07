package controllers;
import model.*;
import scrum_fe.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class ColumnsController implements Initializable {
    @FXML
    private Text boardName;

    @FXML
    private HBox columns;

    @FXML
    private Button createColumn;

    private boolean setBoardTitle(String title) {
        if(title != null)
            boardName.setText(title);
        return title != null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBoardTitle(AppFacade.getInstance().getActiveBoard().getTitle());
        columns = new HBox();
        setUpHBox(columns);
    }

    private void setUpHBox(HBox hbox) {
        ArrayList<Column> boardColumns = AppFacade.getInstance().getActiveBoard().getColumns();
        for(Column column : boardColumns) {
            VBox newCol = new VBox();
            setUpVBox(newCol, column);
            hbox.getChildren().add(newCol);
        }
    }

    private void setUpVBox(VBox vbox, Column column) {
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setId(column.getTitle() + "_vbox");
        // add column title to top of VBox
        Label colTitle = new Label();
        colTitle.setText(column.getTitle());
        vbox.getChildren().add(colTitle);
        // button to add task to column below title
        /*Button addTask = new Button();
        addTask.setText("Add Task");
        addTask.setOnAction(e -> {

        });
        vbox.getChildren().add(addTask);*/
        // populate column with buttons for every task in that column
        ArrayList<Task> tasks = column.getTasks();
        for(Task task : tasks) {
            Label taskLabel = new Label();
            taskLabel.setText(task.getName());
            taskLabel.setId(task.getName() + "_label");
            vbox.getChildren().add(taskLabel);
        }
    }

    @FXML
    public void addNewColumn(ActionEvent event) throws IOException {

    }
}
