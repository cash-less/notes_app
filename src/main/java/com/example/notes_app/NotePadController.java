package com.example.notes_app;

import com.example.notes_app.SqlSide.DatabaseManager;
import com.example.notes_app.SqlSide.Status;
import com.example.notes_app.Utils.CurrentUser;
import com.example.notes_app.Utils.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class NotePadController {
    public VBox home_vbox;
    DatabaseManager dbManager = DatabaseManager.getInstance();
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    public void initialize() throws IOException {
        loadNotes();
    }
    private void loadNotes() throws IOException {
        ArrayList<Note> notes;

        notes = dbManager.readNotes();

        if (notes == null){
            return;
        }

        home_vbox.getChildren().clear();

        for(Note note : notes){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("note_template.fxml"));
            AnchorPane note_component = loader.load();

            try {
                TextField title = (TextField) note_component.lookup("#noteTitle");
                TextField content = (TextField) note_component.lookup("#noteContent");
                Button delete_btn = (Button) note_component.lookup("#btnDeleteNote");
                Button edit_btn = (Button) note_component.lookup("#btnEditNote");
                Button cancelEdit_btn = (Button) note_component.lookup("#btnCancelEdit");
                Button updateEdit_btn = (Button) note_component.lookup("#btnUpdateNote");

//                System.out.println("NN " + note.note_id + " " + note.note_title + " " + note.note_content);

                title.setText(note.note_title);
                content.setText(note.note_content);

                delete_btn.setOnAction(e-> {
                    try {
                        deleteNote(note.note_id);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                edit_btn.setOnAction(e-> {
                    title.setEditable(true);
                    content.setEditable(true);
                    cancelEdit_btn.setVisible(true);
                    updateEdit_btn.setVisible(true);
                });

                cancelEdit_btn.setOnAction(e -> {
                    title.setText(note.note_title);
                    title.setEditable(false);

                    content.setText(note.note_content);
                    content.setEditable(false);

                    updateEdit_btn.setVisible(false);
                    cancelEdit_btn.setVisible(false);
                });

                updateEdit_btn.setOnAction(e -> {
                    Status res = updateNote(note.note_id, title.getText(), content.getText());

                    if (res == Status.NOTE_UPDATE_SUCCESS){
                        title.setEditable(false);
                        content.setEditable(false);

                        cancelEdit_btn.setVisible(false);
                        updateEdit_btn.setVisible(false);
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }

            home_vbox.getChildren().add(note_component);
        }
    }

    private Status updateNote(int noteId, String noteTitle, String noteContent) {
        Status res = dbManager.updateNote(noteId, noteTitle, noteContent);

        if (res == Status.NOTE_UPDATE_SUCCESS){
            showAlert(Alert.AlertType.INFORMATION, "Note updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "There is an error while updating note.");
        }

        return res;
    }

    private void deleteNote (int note_id) throws IOException {
        Status res = dbManager.deleteNote(note_id);

        if (res == Status.NOTE_DELETION_SUCCESS){
            showAlert(Alert.AlertType.INFORMATION, "Note successfully deleted.");
            loadNotes();
        } else {
            showAlert(Alert.AlertType.ERROR, "Unable to delete note.");
        }
    }

    void showAlert(Alert.AlertType alertType, String content){
        alert.setAlertType(alertType);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
