module com.example.notes_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.notes_app to javafx.fxml;
    exports com.example.notes_app;
}