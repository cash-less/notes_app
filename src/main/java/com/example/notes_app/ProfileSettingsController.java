package com.example.notes_app;

import com.example.notes_app.SqlSide.DatabaseManager;
import com.example.notes_app.SqlSide.Status;
import com.example.notes_app.Utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileSettingsController {
    public PasswordField pfChangePass;
    public Label lblProfileUsername;
    DatabaseManager dbManager = DatabaseManager.getInstance();
    CurrentUser currentUser = CurrentUser.getInstance();
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    public void initialize(){
        lblProfileUsername.setText(currentUser.getUsername());
    }

    public void onBtnDelAccClick(ActionEvent actionEvent) {
        Status res = dbManager.deleteUser();

        if (res == Status.ACCOUNT_DELETION_SUCCESS){
            showAlert(Alert.AlertType.INFORMATION, "Account deleted successfully.");
            try {
                goToLogIn(actionEvent);
            } catch (IOException ex) {

            }
        }
    }

    public void onBtnUpdateClick(ActionEvent actionEvent) {
        Status res = dbManager.updateUser(pfChangePass.getText());
        if (res == Status.ACCOUNT_UPDATE_SUCCESS){
            showAlert(Alert.AlertType.INFORMATION, "Account password successfully updated.");
            pfChangePass.setText("");
        } else {
            showAlert(Alert.AlertType.ERROR, "Account password update FAILED");
            pfChangePass.setText("");
        }
    }

    void goToLogIn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Log-in Page");
    }

    void showAlert(Alert.AlertType alertType, String content){
        alert.setAlertType(alertType);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
