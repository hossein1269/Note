package com.example.note.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.note.Adapter.NoteAdapter;
import com.example.note.Database.DbManager;
import com.example.note.Models.Note;
import com.example.note.R;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Note> Notes = new ArrayList<>();
    NoteAdapter Adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.MenuRefresh:
                FillNotes();
                Toast.makeText(this, "تازه سازی با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                break;
            case R.id.MenuAddNote:
                Intent addNoteActivity = new Intent(NotesActivity.this,AddNoteActivity.class);
                startActivity(addNoteActivity);
                break;
            case R.id.MenuPassword:
                Intent ChangePasswordActivity = new Intent(NotesActivity.this, ChangePasswordActivity.class);
                startActivity(ChangePasswordActivity);
                break;
            case R.id.MenuAbout:
                Intent AboutActivity = new Intent(NotesActivity.this, AboutMeActivity.class);
                startActivity(AboutActivity);
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        recyclerView = findViewById(R.id.recy);

        Adapter = new NoteAdapter(Notes,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(Adapter);

        FillNotes();
        Adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        FillNotes();
        super.onResume();
    }

    private void FillNotes(){
        DbManager manager = new DbManager(this);
        Notes.clear();
        Notes.addAll(manager.GetNotes());
        Adapter.notifyDataSetChanged();
        manager.close();
    }
}