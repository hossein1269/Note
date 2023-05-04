package com.example.note.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.note.R;

public class AboutMeActivity extends AppCompatActivity {

    TextView SendMail,Call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablout_me);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        SendMail = findViewById(R.id.Send_mail);
        Call = findViewById(R.id.Call);

        // Handel Send Mail Click
        SendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SendMailIntent = new Intent(Intent.ACTION_SEND);
                SendMailIntent.putExtra(Intent.EXTRA_EMAIL, SendMail.getText());

                SendMailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(SendMailIntent,"ارسال ایمیل"));
            }
        });

        // Call
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CallIntent = new Intent(Intent.ACTION_DIAL);
                CallIntent.setData(Uri.parse("tel:"+Call.getText()));
                startActivity(CallIntent);
            }
        });

    }
}