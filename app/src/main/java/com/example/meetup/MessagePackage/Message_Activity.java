package com.example.meetup.MessagePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetup.ChatPackage.Chat_Activity;
import com.example.meetup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Message_Activity extends AppCompatActivity {

    private ImageView profile_image,meesageBackBtn;
    private TextView reciverFName;
    private RecyclerView MRecyclerView;
    private EditText SendMessageEditText;
    private CardView sendBtn;
    private Bundle bundle;
    private String reciverName,reciverImage,reciverId;
    private String SenderId;
    private MessageAdater adapter;

    String senderBox, reciverBox;

    ArrayList<MessageModel>chatArrylist;

     private MessageAdater adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        profile_image=findViewById(R.id.profile_image);
        reciverFName=findViewById(R.id.reciverFName);
        MRecyclerView=findViewById(R.id.MRecyclerView);
        SendMessageEditText=findViewById(R.id.SendMessageEditText);
        sendBtn=findViewById(R.id.sendBtn);
        meesageBackBtn=findViewById(R.id.meesageBackBtn);


        bundle=getIntent().getExtras();
        reciverName=bundle.getString("UserName");
        reciverImage=bundle.getString("UserImage");
        reciverId=bundle.getString("userAcessCode");

        reciverFName.setText(reciverName);
        Picasso.get().load(reciverImage).into(profile_image);

        ///

        chatArrylist = new ArrayList<>();
        adater = new MessageAdater(Message_Activity.this, chatArrylist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        MRecyclerView.setLayoutManager(linearLayoutManager);
        MRecyclerView.setAdapter(adater);

        ///

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        SenderId=currentUser.getUid();

        senderBox = SenderId+ reciverId;
        reciverBox = reciverId+ SenderId;

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
       DatabaseReference reference1=firebaseDatabase.getReference("users").child(String.valueOf(SenderId));
       DatabaseReference reference2=firebaseDatabase.getReference("message").child(senderBox).child("chats");


       //


        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                chatArrylist.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MessageModel messageModel= dataSnapshot.getValue(MessageModel.class);
                    chatArrylist.add(messageModel);
                }
                adater.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        ///



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });

        meesageBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Message_Activity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });

    }
    private void SendMessage(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();

        String message=SendMessageEditText.getText().toString();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        SenderId=currentUser.getUid();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd-MMMM-yyyy");
        String currentDate=simpleDateFormat1.format(calendar.getTime());


        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("hh:mmaaa");
        String currentTime=simpleDateFormat2.format(calendar.getTime());


        if (message.isEmpty()){
            Toast.makeText(Message_Activity.this, "Message box is empty",
                    Toast.LENGTH_SHORT).show();
        }else {
           //////////



            MessageModel messageModel1 = new MessageModel(message, SenderId, currentTime,currentDate);

            database.getReference().child("message")
                    .child(senderBox)
                    .child("chats")
                    .push()
                    .setValue(messageModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    database.getReference().child("message")
                            .child(reciverBox)
                            .child("chats")
                            .push().setValue(messageModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            });




            //////////////
            SendMessageEditText.setText("");
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Message_Activity.this, Chat_Activity.class);
        startActivity(intent);
    }
}