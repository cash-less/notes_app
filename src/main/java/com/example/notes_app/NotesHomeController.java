package com.example.notes_app;

import com.example.notes_app.SqlSide.DatabaseManager;
import com.example.notes_app.Utils.CurrentUser;
import com.example.notes_app.Utils.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class NotesHomeController {
    public HBox hbox_home;
    @FXML
    private Label welcomeText;
    DatabaseManager dbManager = DatabaseManager.getInstance();
    CurrentUser currentUser = CurrentUser.getInstance();

    @FXML
    public void onLogOutButtonClick(ActionEvent actionEvent) throws IOException {
        currentUser.setUser_id(-1);
        currentUser.setUsername("");
        currentUser.setPassword("");

        goToLogIn(actionEvent);
    }

    public void onBtnProfileClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(NotesApplication.class.getResource("profile-view.fxml"));
        AnchorPane root = loader.load();

        hbox_home.getChildren().remove(1);
        hbox_home.getChildren().add(root);

        HBox.setHgrow(root, Priority.ALWAYS);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Profile Page");
    }
    public void onBtnNotePadClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(NotesApplication.class.getResource("note-pad-view.fxml"));
        AnchorPane root = loader.load();

        hbox_home.getChildren().remove(1);
        hbox_home.getChildren().add(root);

        HBox.setHgrow(root, Priority.ALWAYS);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Notes for u :>");
    }

    void goToLogIn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Log-in Page");
    }
}