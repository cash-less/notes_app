package com.example.notes_app;

import com.example.notes_app.SqlSide.DatabaseManager;
import com.example.notes_app.SqlSide.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogInRegisterController {
    public Button btnLLogIn;
    public Button btnLSignUp;
    public TextField tfLUsername;
    public Label lblLogInError;
    public PasswordField pfLPassword;
    public TextField tfRUsername;
    public PasswordField pfRPassword;
    public Button btnRLogIn;
    public Button btnRSignUp;
    public Label lblRegisterError;
    public PasswordField pfRConfirm;
    public Alert alert = new Alert(Alert.AlertType.NONE);
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    public void onLLoginButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        String username = tfLUsername.getText();
        String pass = pfLPassword.getText();

        if (username.isEmpty() || pass.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Username or password is empty.");
            return;
        }

        Status res = dbManager.login(username, pass.hashCode() + "");

        if (res == Status.LOGIN_SUCCESS){
            goToHomePage(actionEvent);
        } else {
            showAlert(Alert.AlertType.WARNING, "Usernane or password is incorrect.");
        }

        tfLUsername.setText("");
        pfLPassword.setText("");
    }
    public void onLSignUpButtonClick(ActionEvent actionEvent) throws IOException {
        goToRegister(actionEvent);
    }

    public void onRLogInButtonClick(ActionEvent actionEvent) throws IOException {
        goToLogIn(actionEvent);
    }
    public void onRSignUpButtonClick(ActionEvent actionEvent) throws IOException {
        String user = tfRUsername.getText();
        String pass = pfRPassword.getText();
        String cPass = pfRConfirm.getText();

        if (user.isEmpty() || pass.isEmpty() || cPass.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Username or password is empty.");
            return;
        }

        if (cPass.equals(pass)){
            Status createUserRes = dbManager.createUser(user, pass);

            if (createUserRes == Status.REGISTRATION_SUCCESS){
                goToLogIn(actionEvent);
            } else {
                showAlert(Alert.AlertType.WARNING, "Account creation failed.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Passwords do not match.");
        }

        tfRUsername.setText("");
        pfRPassword.setText("");
        pfRConfirm.setText("");
    }
    void showAlert(Alert.AlertType alertType, String content){
        alert.setAlertType(alertType);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    void goToHomePage(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        NotesHomeController controller = fxmlLoader.getController();
//        controller.initialize();
        stage.show();
        stage.setTitle("Notes for u :)");
    }

    void goToLogIn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Log-in Page");
    }

    void goToRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Registration Page");
    }
}