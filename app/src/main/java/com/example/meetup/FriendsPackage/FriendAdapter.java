package com.example.meetup.FriendsPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetup.LoginActivity;
import com.example.meetup.R;
import com.example.meetup.userModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FriendAdapter extends FirebaseRecyclerAdapter<userModel,FriendAdapter.FriendsHolder> {

    Context context;
    String currentHubby;

    public FriendAdapter(@NonNull FirebaseRecyclerOptions<userModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FriendsHolder holder, int position, @NonNull userModel model) {

        context=holder.itemView.getContext();
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(model.getUserId()))
        {

            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);


        }
        /////


        reference.child("friendslist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot p:snapshot.getChildren()) {

                    if (p.getKey().equals(model.getUserId()))
                    {
                        holder.itemView.setVisibility(View.GONE);
                        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                        params.height = 0;
                        params.width = 0;
                        holder.itemView.setLayoutParams(params);
                        System.out.println("hii");
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        ////


       

        holder.FName.setText(model.getName().toUpperCase());
        holder.FHobby.setText(model.getHobby());
        Picasso.get().load(model.getImage()).into(holder.Fimage);

        //



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel userModel=dataSnapshot.getValue(userModel.class);

                currentHubby=userModel.getHobby().toString();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(EmployHome_Activity.this, "Errror", Toast.LENGTH_SHORT).show();

            }
        });


        //

        holder.Frequest_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////
                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                DatabaseReference dReference=firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                //
                FirebaseDatabase fireDatabase= FirebaseDatabase.getInstance();
                DatabaseReference reference1=fireDatabase.getReference("users").
                        child(model.getUserId());




                if (currentHubby.equals(model.getHobby()))
                {

                    //hashmap for current user
                    HashMap presentHashmap=new HashMap();

                    presentHashmap.put(getItem(position).getUserId(),"1");


                    dReference.child("friendslist").updateChildren(presentHashmap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            Toast.makeText(context, "Your are connected",
                                    Toast.LENGTH_SHORT).show();


                        }
                    });

                    //hashmap for friend

                    HashMap fhashmap=new HashMap();

                    fhashmap.put(model.getUserId(),"1");
                    reference1.child("friendslist").updateChildren(fhashmap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                        }
                    });
                }


                else {

                    Toast.makeText(context, "Your hubby not match",
                            Toast.LENGTH_SHORT).show();

                }






                /////

            }
        });


    }

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_friends_row, parent, false);

        return new FriendAdapter.FriendsHolder(view);
    }

    class FriendsHolder extends RecyclerView.ViewHolder{

        TextView FName,FHobby,Frequest_BtnName;
        CardView Frequest_Btn;
        ImageView Fimage;
        public FriendsHolder(@NonNull View itemView) {
            super(itemView);
            FName=itemView.findViewById(R.id.FName);
            FHobby=itemView.findViewById(R.id.FHobby);
            Frequest_Btn=itemView.findViewById(R.id.Frequest_Btn);
            Frequest_BtnName=itemView.findViewById(R.id.Frequest_BtnName);
            Fimage=itemView.findViewById(R.id.Fimage);
        }
    }
}
