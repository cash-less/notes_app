package com.example.notes_app;

import com.example.notes_app.SqlSide.DatabaseManager;
import com.example.notes_app.SqlSide.Status;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewNoteController {

    public TextField tfNoteTitle;
    public TextArea taNoteContent;
    DatabaseManager dbManager = DatabaseManager.getInstance();
    Alert alert = new Alert(Alert.AlertType.NONE);
    void showAlert(Alert.AlertType alertType, String content){
        alert.setAlertType(alertType);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public void onBtnCreateNote(ActionEvent actionEvent) {
        String note_title = tfNoteTitle.getText();
        String note_content = taNoteContent.getText();

        if (note_content.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Note Content is empty. Cannot create new note.");
            return;
        }

        if (note_title.isEmpty()){
            note_title = "untitled";
        }
        Status noteRes = dbManager.createNote(note_title, note_content);

        if (noteRes == Status.NOTE_CREATION_SUCCESS){
            showAlert(Alert.AlertType.INFORMATION, note_title + " is successfully created!");
            tfNoteTitle.setText("");
            taNoteContent.setText("");
        } else {
            showAlert(Alert.AlertType.ERROR, "Note not created.");
        }
    }

}
