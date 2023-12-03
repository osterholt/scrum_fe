package scrum_fe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("welcome"), 1440, 900);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the root frame to the name of the fxml
     * @param fxml NAME of fxml, no .fxml to include
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException { //IF BROKE, REMOVE SCOPE
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}