package com.example.note.Adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.Activities.EditNoteActivity;
import com.example.note.Activities.ViewNoteActivity;
import com.example.note.Database.DbManager;
import com.example.note.Models.Note;
import com.example.note.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    List<Note> Notes;
    Context context;

    public NoteAdapter(List<Note> notes,Context context){
        Notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_design,parent,false);
        return new NoteHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = Notes.get(position);

        holder.Id.setText(note.getId().toString());
        holder.Title.setText(note.getTitle());
        holder.Date.setText(note.getLastModified().toString());
        holder.Description.setText(note.getText());

        // Handel Delete
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialog
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("حذف یادداشت")
                        .setMessage("آیا از حذف "+ holder.Title.getText() +" اطمینان دارید")
                        .setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbManager manager = new DbManager(context);
                                if(manager.DeleteNote(Integer.parseInt(holder.Id.getText().toString()))){
                                    Notes.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();

                                }
                                manager.close();
                            }
                        })
                        .setNegativeButton("خیر",null)
                        .create();
                dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                dialog.show();
            }
        });
        // Handel Edit
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditActivity = new Intent(context, EditNoteActivity.class);
                EditActivity.putExtra("Id",holder.Id.getText());
                context.startActivity(EditActivity);
            }
        });
        // Handel Open Note View
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewNoteActivity = new Intent(context, ViewNoteActivity.class);
                ViewNoteActivity.putExtra("Id",holder.Id.getText());
                context.startActivity(ViewNoteActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Notes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder
    {
        public TextView Date,Title, Description,Id;
        public ImageButton Delete,Edit;
        public LinearLayout layout;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.noteId);
            Date = itemView.findViewById(R.id.lblLastChange);
            Title = itemView.findViewById(R.id.lblTitle);
            Description = itemView.findViewById(R.id.summry);
            Delete = itemView.findViewById(R.id.deleteBtn);
            Edit = itemView.findViewById(R.id.EditBtn);
            layout = itemView.findViewById(R.id.TitleBar);

        }
    }
}
