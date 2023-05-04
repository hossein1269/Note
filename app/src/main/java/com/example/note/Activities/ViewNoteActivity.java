package com.example.note.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import com.example.note.Database.DbManager;
import com.example.note.Models.Note;
import com.example.note.R;

public class ViewNoteActivity extends AppCompatActivity {

    TextView Text;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Text = findViewById(R.id.ViewText);

        String Id = getIntent().getExtras().getString("Id");
        if(Id == null)
            finish();

        DbManager manager = new DbManager(this);
        Note note = manager.GetNoteById(Id);
        manager.close();

        getSupportActionBar().setTitle(note.getTitle() + " | " +note.getLastModified());
        Text.setText(note.getText());
    }
}