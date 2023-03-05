package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapte extends RecyclerView.Adapter<MessageAdapte.MyViewHolder> {
    private Context context;
    private List<MessageModel> messageList;

    public MessageAdapte(Context context) {
        this.context = context;
        messageList =new ArrayList<>();
    }
    public void add(MessageModel messageModel){
        messageList.add(messageModel);
        notifyDataSetChanged();
    }
    public void clear(){
        messageList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageAdapte.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.message_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapte.MyViewHolder holder, int position) {
        holder.username.setText(messageList.get(position).getMessage().toString());
        if (messageList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.teal_200));
            holder.username.setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.black));
            holder.username.setTextColor(context.getResources().getColor(R.color.white));

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.chatMessage);
            cardView=itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
