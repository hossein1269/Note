package com.example.note.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.Database.DbManager;
import com.example.note.Models.Note;
import com.example.note.R;

public class EditNoteActivity extends AppCompatActivity {

    Button EditBtn;
    EditText Title,Text;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Get Widget From Activity
        EditBtn = findViewById(R.id.EditNoteBtn);
        Title = findViewById(R.id.EditTitle);
        Text = findViewById(R.id.EditText);

        // Fill Note Field By Id
        Bundle bundle = getIntent().getExtras();
        String id =  bundle.getString("Id");
        if(id == null)
            finish();
        Id= id ;
        DbManager manager = new DbManager(this);
        Note note = manager.GetNoteById(id);
        // Set Note Field From Note Instance
        Title.setText(note.getTitle());
        Text.setText(note.getText());
        manager.close();

        // Handel EditBtn Clicked
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Title.getText().toString();
                String text = Text.getText().toString();
                String id = Id;
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(text)){
                    Toast.makeText(EditNoteActivity.this, "تمامی فیلد باید مقدار داشته باشد", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbManager manager = new DbManager(EditNoteActivity.this);
                    if(manager.EditNote(title,text,id)){
                        Toast.makeText(EditNoteActivity.this, "یادداشت اضافه شد", Toast.LENGTH_SHORT).show();
                        manager.close();
                        finish();
                    }
                    else {
                        Toast.makeText(EditNoteActivity.this, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}