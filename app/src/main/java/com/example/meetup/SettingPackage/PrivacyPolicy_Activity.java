package com.example.meetup.SettingPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.meetup.R;

public class PrivacyPolicy_Activity extends AppCompatActivity {
    private ImageView privacyBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy_activity);

        privacyBack_btn=findViewById(R.id.privacyBack_btn);

        privacyBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrivacyPolicy_Activity.this,Setting_Activity.class);
                startActivity(intent);
            }
        });
    }
}