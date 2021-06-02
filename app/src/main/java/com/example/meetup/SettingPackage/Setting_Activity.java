package com.example.meetup.SettingPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.example.meetup.FriendsPackage.FindFrindes_Activity;
import com.example.meetup.LoginActivity;
import com.example.meetup.ProfilePackage.Profile_Activity;
import com.example.meetup.R;
import com.example.meetup.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Setting_Activity extends AppCompatActivity {

    private LinearLayout logout_Btn,contact_Btn,Privacy_Btn,chatBtn,findFBtn,profileBtn;
    private TextView Nametxt;

    private AlertDialog.Builder alertDialog;
    private ImageView Sprofile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        logout_Btn=findViewById(R.id.Logout_Btn);
        contact_Btn=findViewById(R.id.contact_Btn);
        Sprofile_image=findViewById(R.id.Sprofile_image);
        Nametxt=findViewById(R.id.Nametxt);
        Privacy_Btn=findViewById(R.id.Privacy_Btn);
        chatBtn=findViewById(R.id.chatBtn);
        findFBtn=findViewById(R.id.findFBtn);
        profileBtn=findViewById(R.id.profileBtn);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting_Activity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });
        findFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting_Activity.this, FindFrindes_Activity.class);
                startActivity(intent);
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting_Activity.this, Profile_Activity.class);
                startActivity(intent);
            }
        });

        Privacy_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting_Activity.this,PrivacyPolicy_Activity.class);
                startActivity(intent);
            }
        });

        logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutfunction();
            }
        });
        contact_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmailFunction();
            }
        });

        FirebaseDatabase fDatabase= FirebaseDatabase.getInstance();
        DatabaseReference dReference=fDatabase.getReference("users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        dReference.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link=snapshot.getValue(String.class);
                Picasso.get().load(link).resize(100, 100).
                        into(Sprofile_image);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userModel model=dataSnapshot.getValue(userModel.class);


                Nametxt.setText(model.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void logoutfunction() {

        alertDialog=new AlertDialog.Builder(Setting_Activity.this);
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Setting_Activity.this,"Logged out",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Setting_Activity.this, LoginActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                ///

            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
        //



        //
    }

    private void  sendmailFunction(){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri emailSend = Uri.parse("mailto:amiyaranjanmajhi123@gmail.com");
        intent.setData(emailSend);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Setting_Activity.this, Profile_Activity.class);
        startActivity(intent);
    }
}