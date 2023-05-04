package com.example.note.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.note.Models.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DbManager extends SQLiteOpenHelper {
    private final static String Name = "Note.db";
    private final static Integer Version = 1;

    private String UserTable = "Users";
    private String NoteTable = "Notes";

    public DbManager(@Nullable Context context) {
        super(context, Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String UsersQuery = "CREATE TABLE "+ UserTable +"(\n" +
                " [Id] INTEGER PRIMARY KEY ASC AUTOINCREMENT, \n" +
                " [Username] VARCHAR(64) NOT NULL, \n" +
                " [Password] VARCHAR(64) NOT NULL);";
        db.execSQL(UsersQuery);
        db.execSQL("INSERT INTO " + UserTable + "(Username,Password) VALUES('Admin','12345');");
        // Create Note Table
        String NoteQuery = "CREATE TABLE "+ NoteTable +"(\n" +
                "  [Id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  [Title] VARCHAR(100) NOT NULL, \n" +
                "  [Text] TEXT, \n" +
                "  [CreateDate] DATETIME, \n" +
                "  [LastChange] DATETIME);";
        db.execSQL(NoteQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean CheckLogin(String username,String password){
        SQLiteDatabase connection = this.getReadableDatabase();
        Cursor cursor = connection.rawQuery("SELECT * FROM "+ UserTable +" WHERE Username=? AND Password=?",new String[]{username,password});
        if(cursor.moveToFirst()){
            return true;
        }
        return  false;
    }
    public boolean UpdateLogin(String username,String password){
        ContentValues values = new ContentValues();
        values.put("Username",username);
        values.put("Password",password);

        SQLiteDatabase connection = this.getWritableDatabase();
        if(connection.update(UserTable,values,"Id=1",null) == 0){
            return false;
        }
        return true;
    }
    public  Note GetNoteById(String id){
        SQLiteDatabase connection = this.getReadableDatabase();
        Cursor cursor = connection.rawQuery("SELECT * FROM " + NoteTable +" WHERE Id="+id,null);
        if(cursor.moveToFirst()){
            Note note = new Note();
            note.setTitle(cursor.getString(1));
            note.setText(cursor.getString(2));
            note.setCreatedDate(LocalDate.parse(cursor.getString(3)));
            note.setLastModified(LocalDate.parse(cursor.getString(4)));
            return note;
        }
        return null;
    }

    public List<Note> GetNotes(){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase connection = this.getReadableDatabase();
        Cursor cursor = connection.rawQuery("SELECT * FROM " + NoteTable,null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setText(cursor.getString(2));
                note.setCreatedDate(LocalDate.parse(cursor.getString(3)));                 note.setLastModified(LocalDate.parse(cursor.getString(4)));
                notes.add(note);
            }
            while (cursor.moveToNext());
        }
        return notes;
    }
    public boolean AddNote(String title,String text){
        String currentDate = LocalDate.now().toString();
        ContentValues values = new ContentValues();
        values.put("Title",title);
        values.put("Text",text);
        values.put("CreateDate",currentDate);
        values.put("LastChange",currentDate);
        SQLiteDatabase connection = this.getWritableDatabase();
        Long res =  connection.insert(NoteTable,null,values);
        if(res == -1){
            return false;
        }
        return true;
    }

    public boolean EditNote(String title,String text,String id){
        String currentDate = LocalDate.now().toString();
        ContentValues values = new ContentValues();
        values.put("Title",title);
        values.put("Text",text);
        values.put("LastChange",currentDate);

        SQLiteDatabase connection = this.getWritableDatabase();
        Integer res = connection.update(NoteTable,values,"Id=?",new String[]{id});
        if(res == 0){
            return false;
        }
        return true;

    }

    public boolean DeleteNote(Integer id){
        SQLiteDatabase connection = this.getWritableDatabase();
        int result = connection.delete(NoteTable,"Id=?",new String[]{ String.valueOf(id) });
        if(result == 0)
            return false;
        return true;
    }
}
