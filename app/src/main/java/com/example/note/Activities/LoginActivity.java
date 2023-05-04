package com.example.note.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.Database.DbManager;
import com.example.note.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        // This Context
        Context context = this;
        // Get Widget
        Button login = findViewById(R.id.BtnLogin);
        EditText password = findViewById(R.id.Editpassword);
        EditText username = findViewById(R.id.EditUsername);

        // Handel Login Clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = password.getText().toString().trim();
                String usernameText = username.getText().toString().trim();
                if(TextUtils.isEmpty(passwordText)){
                    password.requestFocus();
                    password.setError("رمز عبور خالی می باشد");
                    return;
                }
                if(TextUtils.isEmpty(usernameText)){
                    username.requestFocus();
                    username.setError("نام کاربری خالی می باشد");
                    return;
                }
                DbManager manager = new DbManager(context);
                if(manager.CheckLogin(usernameText,passwordText)){
                    Intent NoteIntent = new Intent(LoginActivity.this,NotesActivity.class);
                    startActivity(NoteIntent);
                    finish();
                }
                else{
                    Toast.makeText(context, "رمز عبور با نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                }
                manager.close();
            }
        });
    }
}