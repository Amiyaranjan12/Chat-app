package com.example.meetup.FriendsPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.example.meetup.ProfilePackage.Profile_Activity;
import com.example.meetup.R;
import com.example.meetup.SettingPackage.Setting_Activity;
import com.example.meetup.userModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindFrindes_Activity extends AppCompatActivity {

    private LinearLayout chat_Btn,Fprofile_Btn,Psetting_btn;
    private RecyclerView FriendRecyclerview;
    private FriendAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private SearchView FsearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_frindes_activity);


        FriendRecyclerview=findViewById(R.id.FriendRecyclerview);
        FsearchView=findViewById(R.id.FsearchView);
        chat_Btn=findViewById(R.id.chat_Btn);
        Fprofile_Btn=findViewById(R.id.Fprofile_Btn);
        Psetting_btn=findViewById(R.id.Psetting_btn);



        FriendRecyclerview.setLayoutManager(new LinearLayoutManager(this));



        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("users");

        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(databaseReference, userModel.class)
                        .build();


        adapter=new FriendAdapter(options);
        FriendRecyclerview.setAdapter(adapter);

        //
        FsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s_text) {

                searchView_function(s_text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s_text) {
                searchView_function(s_text);
                return false;
            }
        });

        //

        Psetting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindFrindes_Activity.this, Setting_Activity.class);
                startActivity(intent);
            }
        });

        chat_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindFrindes_Activity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });

        Fprofile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindFrindes_Activity.this,Chat_Activity.class);
                startActivity(intent);
            }
        });
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

    private void searchView_function(String s_text) {
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("users");

        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(databaseReference.orderByChild("hobby")
                                .startAt(s_text.toUpperCase()).endAt(s_text+"\uf8ff"), userModel.class)
                        .build();


        adapter=new FriendAdapter(options);
        adapter.startListening();
        FriendRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(FindFrindes_Activity.this, Chat_Activity.class);
        startActivity(intent);
    }
}