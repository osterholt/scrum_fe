module scrum_fe {
    requires javafx.controls;
    requires javafx.fxml;

    opens scrum_fe to javafx.fxml;
    exports scrum_fe;

    opens controllers to javafx.fxml;
    exports controllers;
}
