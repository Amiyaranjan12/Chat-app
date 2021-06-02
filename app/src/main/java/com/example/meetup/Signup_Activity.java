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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup_Activity extends AppCompatActivity {

    private EditText signupName,signupEmail,signupHubby,signupPassword,signupCpassword;
    private TextView signupLogin_btn;
    private ImageView signup_Back;
    private CardView signupBtn;
    private FirebaseAuth Auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        signupName=findViewById(R.id.signupName);
        signupEmail=findViewById(R.id.signupEmail);
        signupHubby=findViewById(R.id.signupHubby);
        signupPassword=findViewById(R.id.signupPassword);
        signupCpassword=findViewById(R.id.signupCpassword);
        signupBtn=findViewById(R.id.signupBtn);
        signup_Back=findViewById(R.id.signup_Back);
        signupLogin_btn=findViewById(R.id.signupLogin_btn);

        Auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupFunction();
            }
        });
        signup_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backfunction();
            }
        });
        signupLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backfunction();
            }
        });

    }

    private void signupFunction() {

        //
        String Username = signupName.getText().toString();
        String Useremail = signupEmail.getText().toString();
        String Userhubby = signupHubby.getText().toString().toUpperCase();
        String Userpassword = signupPassword.getText().toString();
        String UserCPassword = signupCpassword.getText().toString();



        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.login_progressdialog);

        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        progressDialog.setCanceledOnTouchOutside(false);

        //

        if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Useremail) ||
                TextUtils.isEmpty(Userhubby) || TextUtils.isEmpty(Userpassword) || TextUtils.isEmpty(UserCPassword)) {
            progressDialog.dismiss();
            Toast.makeText(Signup_Activity.this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
        }

        else if (!Userpassword.equals(UserCPassword)) {
            progressDialog.dismiss();
            Toast.makeText(Signup_Activity.this, "Password does not Match", Toast.LENGTH_SHORT).show();
        }
        else {

            //
            Auth.createUserWithEmailAndPassword(Useremail,Userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        DatabaseReference reference = database.getReference().child("users").child(Auth.getUid());

                        HashMap signupHashmap=new HashMap();

                        signupHashmap.put("name",Username);
                        signupHashmap.put("email",Useremail);
                        signupHashmap.put("hobby",Userhubby);
                        signupHashmap.put("userId",Auth.getUid());

                        signupHashmap.put("image","https://firebasestorage.googleapis.com/v0/b/meetup-83fc9.appspot.com/o/common.jpeg?alt=media&token=965124ad-922e-44e8-94b9-6bdd3bc82b5e");



                        reference.updateChildren(signupHashmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if(task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(Signup_Activity.this, Chat_Activity.class);
                                    startActivity(intent);
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Signup_Activity.this, "Error in Creating a New user", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                        /*
                        //


                        DatabaseReference reference = database.getReference().child("users").child(Auth.getUid());

                        userModel user=new userModel(Userid,Username,Useremail,Userhubby,"yhiuhiojiojijuh");
                        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(Signup_Activity.this, Chat_Activity.class);
                                    startActivity(intent);
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Signup_Activity.this, "Error in Creating a New user", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        //

                         */
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(Signup_Activity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            //
        }


    }

    private void backfunction(){

        Intent intent=new Intent(Signup_Activity.this,LoginActivity.class);
        startActivity(intent);
    }

}
