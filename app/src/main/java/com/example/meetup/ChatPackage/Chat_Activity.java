package com.example.meetup.ChatPackage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.meetup.FriendsPackage.FindFrindes_Activity;
import com.example.meetup.FriendsPackage.FriendAdapter;
import com.example.meetup.ProfilePackage.Profile_Activity;
import com.example.meetup.R;
import com.example.meetup.SettingPackage.Setting_Activity;
import com.example.meetup.userModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat_Activity extends AppCompatActivity {

    private LinearLayout findF_Btn,profile_Btn,Csetting_btn;
    private RecyclerView chatlist_recyclerview;
    private Chatlist_adapter adapter;
    private SearchView CsearchView;
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        findF_Btn=findViewById(R.id.findF_Btn);
        profile_Btn=findViewById(R.id.profile_Btn);
        chatlist_recyclerview=findViewById(R.id.chatlist_recyclerview);
        CsearchView=findViewById(R.id.CsearchView);
        Csetting_btn=findViewById(R.id.Csetting_btn);

        chatlist_recyclerview.setLayoutManager(new LinearLayoutManager(this));



       FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("users");

        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(databaseReference, userModel.class)
                        .build();


        adapter=new Chatlist_adapter(options);
        chatlist_recyclerview.setAdapter(adapter);

        Csetting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat_Activity.this, Setting_Activity.class);
                startActivity(intent);
            }
        });

        findF_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat_Activity.this, FindFrindes_Activity.class);
                startActivity(intent);
            }
        });
        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Chat_Activity.this, Profile_Activity.class);
                startActivity(intent);
            }
        });


        //
        CsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s_text) {

                ChatsearchView_function(s_text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s_text) {
                ChatsearchView_function(s_text);
                return false;
            }
        });

        //

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        exitfunction();
    }

    private void ChatsearchView_function(String s_text) {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("users");

        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(databaseReference.orderByChild("name")
                                .startAt(s_text).endAt(s_text+"\uf8ff"), userModel.class)
                        .build();


        adapter=new Chatlist_adapter(options);
        adapter.startListening();
        chatlist_recyclerview.setAdapter(adapter);
    }

    private void exitfunction() {

        alertDialog=new AlertDialog.Builder(Chat_Activity.this);
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///
                finish();
                moveTaskToBack(true);
                System.exit(0);

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
}