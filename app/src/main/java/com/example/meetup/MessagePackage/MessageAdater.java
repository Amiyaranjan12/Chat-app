package com.example.meetup.MessagePackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetup.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

class MessageAdater extends RecyclerView.Adapter {

    Context context;
    ArrayList<MessageModel> chatArrylist;
    int senderChat = 0;
    int reciverChat = 1;

    public MessageAdater(Context context, ArrayList<MessageModel> chatArrylist) {
        this.context = context;
        this.chatArrylist = chatArrylist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == senderChat) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_row, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_row, parent, false);
            return new ReciverViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = chatArrylist.get(position);

        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.senderMessage.setText(messageModel.getMessage());
            viewHolder. senderMTime.setText(messageModel.getMessageTime());

        } else {
            ReciverViewHolder viewHolder = (ReciverViewHolder) holder;
            viewHolder.reciverMessage.setText(messageModel.getMessage());
            viewHolder. reciverMTime.setText(messageModel.getMessageTime());

        }

    }

    @Override
    public int getItemCount() {
        return chatArrylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = chatArrylist.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messageModel.getSenderId())) {
            return senderChat;
        } else {
            return reciverChat;
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {


        TextView senderMessage,senderMTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);


            senderMessage = itemView.findViewById(R.id.senderMessage);
            senderMTime=itemView.findViewById(R.id.senderMTime);

        }
    }

    class ReciverViewHolder extends RecyclerView.ViewHolder {


        TextView reciverMessage,reciverMTime;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);


            reciverMessage = itemView.findViewById(R.id.reciverMessage);
            reciverMTime=itemView.findViewById(R.id.reciverMTime);

        }
    }
}