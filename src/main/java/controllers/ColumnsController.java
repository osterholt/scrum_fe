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
    //ideally all this code works, but i couldn't test it with the funcitonality of the app without
    // the boards screen before it being done, and that couldn't be finished until the JSON was fixed :/
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
        Button addTask = new Button();
        addTask.setText("Add Task");
        addTask.setOnAction(e -> {
            //TODO: add functionality to create a new task object within the vbox (column)
            // will likely be a new screen, but maybe a popup would work? it would definitely be cleaner, but idk how to do that rn haha
        });
        vbox.getChildren().add(addTask);
        // populate column with label for every task in that column
        ArrayList<Task> tasks = column.getTasks();
        for(Task task : tasks) {
            //TODO: right now, I just have a label to click what i would imagine would pull up a screen with all the task info
            // portia mentioned using a pane instead earlier today in class, but I haven't been able to figure out which kind of pane to use
            // I think it would be cool to use a pane with some information, and when you click it have a pop up with all the extra information show up
            // or maybe when hovering over it, but like I said above, couldn't figure it out at the moment
            Label taskLabel = new Label();
            taskLabel.setText(task.getName());
            taskLabel.setId(task.getName() + "_label");
            vbox.getChildren().add(taskLabel);
        }
    }

    @FXML
    public void addNewColumn(ActionEvent event) throws IOException {
        //TODO: same as add task above, but with a column. volumns are VBoxes
    }
}
