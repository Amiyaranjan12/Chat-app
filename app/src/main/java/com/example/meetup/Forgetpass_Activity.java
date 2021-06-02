package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpass_Activity extends AppCompatActivity {

    private EditText editTextForgetUser;
    private RelativeLayout Forget_userbtn,Forget_Userback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpass_activity);

        editTextForgetUser=findViewById(R.id.editTextForgetUser);
        Forget_userbtn=findViewById(R.id.Forget_userbtn);
        Forget_Userback=findViewById(R.id.Forget_Userback);

        Forget_userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassworduser();

            }
        });

        Forget_Userback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Forgetpass_Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void forgetPassworduser() {

        String email=editTextForgetUser.getText().toString();

        if(TextUtils.isEmpty(email)){
            editTextForgetUser.setError("PLEASE ENTER YOUR EMAIL");
            return;

        }

        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(Forgetpass_Activity.this,"PLEASE CHECK YOUR MAILBOX",Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(Forgetpass_Activity.this,"PLEASE ENTER VALID EMAIL ADDRESS",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}