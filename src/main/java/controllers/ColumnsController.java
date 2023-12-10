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
import javafx.scene.layout.TilePane;

public class ColumnsController implements Initializable {
    @FXML
    private Button addTasks;

    @FXML
    private Text boardName;

    @FXML
    private TilePane burger1;

    @FXML
    private TilePane burger2;

    @FXML
    private TilePane burger3;

    @FXML
    private HBox columns;

    @FXML
    private Button createColumn;

    @FXML
    private TilePane curve1;

    @FXML
    private TilePane curve2;

    @FXML
    private TilePane init;

    @FXML
    private Button leftbutton1;

    @FXML
    private Button leftbutton2;

    @FXML
    private Button leftbutton3;

    @FXML
    private Button leftbutton4;

    @FXML
    private Button leftbutton5;

    @FXML
    private Button leftbutton6;

    @FXML
    private Button rightbutton1;

    @FXML
    private Button rightbutton2;

    @FXML
    private Button rightbutton3;

    @FXML
    private Button rightbutton4;

    @FXML
    private Button rightbutton5;

    @FXML
    private Button rightbutton6;

    @FXML
    private TextField textfield;

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
        vbox.setPrefWidth(350);
        // add column title to top of VBox
        Label colTitle = new Label();
        colTitle.setText(column.getTitle());
        colTitle.setAlignment(Pos.CENTER);
        colTitle.setPrefWidth(350);
        vbox.getChildren().add(colTitle);
        // button to add task to column below title
        Button addTask = new Button();
        addTask.setText("Add Task");
        addTask.setOnAction(e -> {
        });
        vbox.getChildren().add(addTask);
        // populate column with label for every task in that column
        ArrayList<Task> tasks = column.getTasks();
        for(Task task : tasks) {
            Label taskLabel = new Label();
            taskLabel.setText(task.getName());
            taskLabel.setId(task.getName() + "_label");
            vbox.getChildren().add(taskLabel);
        }
    }

    @FXML
    void pushright1(ActionEvent action) throws IOException{
        curve1.setVisible(false);
        curve2.setVisible(true);
    }

    @FXML
    void pushright3(ActionEvent action) throws IOException {
        burger1.setVisible(false);
        burger2.setVisible(true);
    }

    @FXML
    void pushright5(ActionEvent action) throws IOException {
        burger2.setVisible(false);
        burger3.setVisible(true);
    }

    public TilePane getInitTilePane() {
        return init;
    }

    @FXML
    void taskButton(ActionEvent action) throws IOException {
        App.setRoot("task");
    }
}