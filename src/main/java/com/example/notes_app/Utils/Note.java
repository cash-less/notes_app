package com.example.notes_app.Utils;

public class Note {
    public int note_id;
    public String note_title;
    public String note_content;

    public Note (int note_id, String note_title, String note_content){
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
    }
}
