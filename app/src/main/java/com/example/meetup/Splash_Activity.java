package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Activity extends AppCompatActivity {

    private static final int SCREENTIME_OUT =2000 ;
    private boolean internetStatus= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);


        ///internet checker
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            internetStatus = true;
        }
        else
            internetStatus = false;

        ////

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //
                if (internetStatus==true)
                {
                    keeploginFunction();
                }

                if (internetStatus==false)
                {
                    Toast.makeText(Splash_Activity.this, "Check your internet connection",
                            Toast.LENGTH_SHORT).show();
                }

               //
            }
        },SCREENTIME_OUT);

    }

    private void keeploginFunction() {

        FirebaseAuth   mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        if (user!= null) {

            Intent intent=new Intent(Splash_Activity.this, Chat_Activity.class);
            startActivity(intent);
            finish();

        }

        else {
            Intent intent=new Intent(Splash_Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

    }
}