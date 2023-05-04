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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText Username,Password;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Username = findViewById(R.id.NewUsername);
        Password = findViewById(R.id.NewPassword);
        Save = findViewById(R.id.BtnUpdate);

        // Handel Save New Login Info
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                if(TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
                    Toast.makeText(ChangePasswordActivity.this, "تمامی فیلد ها باید مقدار داشته باشد", Toast.LENGTH_SHORT).show();
                }
                else {
                    DbManager manager = new DbManager(ChangePasswordActivity.this);
                    if(manager.UpdateLogin(username,password)){
                        Toast.makeText(ChangePasswordActivity.this, "اطلاعات ورود با موفقیت تغییر یافت", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                    }
                    manager.close();
                    finish();
                }
            }
        });
    }
}