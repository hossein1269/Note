package com.example.note.Models;
public class User {
    private Integer Id;
    private String Password;

    public User(){
    }

    public User(Integer id, String password){
        Id = id;
        Password = password;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
