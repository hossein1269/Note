package com.example.note.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.Database.DbManager;
import com.example.note.R;

public class AddNoteActivity extends AppCompatActivity {

    EditText Title,Text;
    Button AddNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Title = findViewById(R.id.Title);
        Text = findViewById(R.id.Text);
        AddNote = findViewById(R.id.AddNoteBtn);

        // Handel Add Note Button
        AddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Title.getText().toString();
                String text = Text.getText().toString();
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(text)){
                    Toast.makeText(AddNoteActivity.this, "تمامی فیلد باید مقدار داشته باشد", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbManager manager = new DbManager(AddNoteActivity.this);
                    if(manager.AddNote(title,text)){
                        Toast.makeText(AddNoteActivity.this, "یادداشت اضافه شد", Toast.LENGTH_SHORT).show();
                        manager.close();
                        finish();
                    }
                    else {
                        Toast.makeText(AddNoteActivity.this, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}