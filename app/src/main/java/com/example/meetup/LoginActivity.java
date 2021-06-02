package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity" ;
    private TextView signup_textBtn,forget_Btn;
    private EditText login_id,login_pass;
    private CardView loginBtn;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        signup_textBtn=findViewById(R.id.signup_textBtn);
        login_id=findViewById(R.id.login_id);
        login_pass=findViewById(R.id.login_pass);
        loginBtn=findViewById(R.id.loginBtn);
        forget_Btn=findViewById(R.id.forget_Btn);


        mAuth=FirebaseAuth.getInstance();


        forget_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,Forgetpass_Activity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFunction();
            }
        });

        signup_textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,Signup_Activity.class);
                startActivity(intent);
            }
        });
    }
        ///////////////////


       private void LoginFunction() {



        String email=login_id.getText().toString();
        String  password=login_pass.getText().toString();

        if(TextUtils.isEmpty(email)){
            login_id.setError("PLEASE ENTER YOUR EMAIL");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            login_pass.setError("PLEASE ENTER YOUR PASSWORD");
            return;
        }



        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.show();
       progressDialog.setContentView(R.layout.login_progressdialog);

        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
      //  progressDialog.setMessage("processing....");

        progressDialog.setCanceledOnTouchOutside(false);



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                          Intent intent=new Intent(LoginActivity.this, Chat_Activity.class);
                          startActivity(intent);

                        } else {


                            Toast.makeText(LoginActivity.this, "Check your id and password",
                                    Toast.LENGTH_SHORT).show();

                        }
                     progressDialog.dismiss();


                    }

                });
    }





         ////////
}