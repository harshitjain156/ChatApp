package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    String receiverId;
    DatabaseReference databaseReferenceSender;
    DatabaseReference databaseReferenceReceiver;

    String senderRoom,receiverRoom;
    MessageAdapter messageAdapte;
    RecyclerView recyclerView;
    Button send;
    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiverId=getIntent().getStringExtra("id");
        messageAdapte=new MessageAdapter(this);
        send=findViewById(R.id.sendmsg);
        recyclerView=findViewById(R.id.recycleviewChat);
        msg=findViewById(R.id.msg);
        recyclerView.setAdapter(messageAdapte);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        senderRoom= FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom=receiverId+FirebaseAuth.getInstance().getUid();
        databaseReferenceSender= FirebaseDatabase.getInstance().getReference().child("chats").child(senderRoom);
        databaseReferenceReceiver= FirebaseDatabase.getInstance().getReference().child("chats").child(receiverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapte.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageAdapte.add(messageModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=msg.getText().toString();
                msg.setText("");
                if (message.trim().length()>0){
                    sendMessage(message);

                }
            }
        });
    }

    private void sendMessage(String message) {
        String id = UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(id,FirebaseAuth.getInstance().getUid(),message);
        messageAdapte.add(messageModel);
        databaseReferenceSender
                .child(id)
                .setValue(messageModel);
        databaseReferenceReceiver
                .child(id)
                .setValue(messageModel);
    }
}