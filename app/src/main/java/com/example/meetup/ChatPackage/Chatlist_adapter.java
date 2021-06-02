package com.example.meetup.ChatPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetup.FriendsPackage.FriendAdapter;
import com.example.meetup.MessagePackage.Message_Activity;
import com.example.meetup.R;
import com.example.meetup.userModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Chatlist_adapter extends FirebaseRecyclerAdapter<userModel, Chatlist_adapter.ChatlistHolder> {


    Context context;
    public Chatlist_adapter(@NonNull FirebaseRecyclerOptions<userModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatlistHolder holder, int position, @NonNull userModel model) {


        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(model.getUserId()))
        {

            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);


        }

        //
        DatabaseReference databaseReference1=firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("friendslist");


         databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.hasChild(model.getUserId())){
                     holder.CName.setText(model.getName().toUpperCase());
                     holder.CHobby.setText(model.getHobby());
                     Picasso.get().load(model.getImage()).into(holder.Userimage);
                 }
                 else {

                     holder.itemView.setVisibility(View.GONE);
                     ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                     params.height = 0;
                     params.width = 0;
                     holder.itemView.setLayoutParams(params);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        //

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(model.getUserId())){


                            ////
                            AlertDialog.Builder alertDialog;
                            alertDialog=new AlertDialog.Builder(context);
                            alertDialog.setMessage("Do you want to remove?");
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ///
                                    databaseReference1.child(model.getUserId()).removeValue();
                                    notifyDataSetChanged();

                                    ///

                                }
                            });

                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog dialog=alertDialog.create();
                            dialog.show();
                            //
                            ///
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        ///



    }

    @NonNull
    @Override
    public ChatlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatlist_row, parent, false);

        return new Chatlist_adapter.ChatlistHolder(view);
    }

    class ChatlistHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView CName,CHobby,removeBtn;
        ImageView Userimage;
        public ChatlistHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            CName=itemView.findViewById(R.id.CName);
            CHobby=itemView.findViewById(R.id.CHobby);
            Userimage=itemView.findViewById(R.id.Userimage);
            removeBtn=itemView.findViewById(R.id.removeBtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            int rowPosition=getAdapterPosition();
            Intent intent=new Intent(context, Message_Activity.class);
            intent.putExtra("userAcessCode",getItem(rowPosition).getUserId());
            intent.putExtra("UserName",getItem(rowPosition).getName());
            intent.putExtra("UserImage",getItem(rowPosition).getImage());
            context.startActivity(intent);

        }
    }
}
