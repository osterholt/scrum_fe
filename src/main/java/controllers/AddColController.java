package controllers;

import java.io.IOException;
import scrum_fe.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.scene.Parent;

public class AddColController {

    @FXML
    private Button createTask;

    @FXML
    void createColumn(ActionEvent event) throws IOException {
      
      //try {
        /* 
        FXMLLoader loader = new FXMLLoader(TaskController.class.getResource("columns.fxml"));
        Parent root = loader.load();
        ColumnsController columnsController = loader.getController();
        TilePane initTilePane = columnsController.getInitTilePane();
        initTilePane.setVisible(true);*/
      //} catch(IOException e) {
        //e.printStackTrace();
      //}

      App.setRoot("columns2");
    }

}

