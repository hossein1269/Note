package com.example.note.Models;

import java.time.LocalDate;

public class Note {

    private Integer Id;
    private String Title;
    private String Text;
    private LocalDate CreatedDate;
    private LocalDate LastModified;

    public Note(){

    }
    public Note(String title,String text){
        Title = title;
        Text = text;
        LocalDate current = LocalDate.now();
        CreatedDate = current;
        LastModified = current;
    }
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public LocalDate getCreatedDate() {
        return CreatedDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        CreatedDate = createdDate;
    }

    public LocalDate getLastModified() {
        return LastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        LastModified = lastModified;
    }

}
