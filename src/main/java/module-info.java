module uas.melvin {
    requires javafx.controls;
    requires javafx.fxml;

    opens uas.melvin to javafx.fxml;
    exports uas.melvin;
}
