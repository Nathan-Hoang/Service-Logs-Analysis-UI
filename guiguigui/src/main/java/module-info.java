module com.test_gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.test_gui to javafx.fxml;
    exports com.test_gui;
}
 