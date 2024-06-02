module com.example.notes_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.notes_app to javafx.fxml;
    exports com.example.notes_app;
}