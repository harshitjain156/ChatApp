package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText username,email,password;
    Button signup;
    TextView login;
    String Email,Username,Password;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=findViewById(R.id.Signup_username);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");

        email=findViewById(R.id.SignupEmail);
        password=findViewById(R.id.signUpPassword);
        login=findViewById(R.id.signupTologin);
        signup=findViewById(R.id.SignUpButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString().trim();
                Username=username.getText().toString().trim();
                Password=password.getText().toString().trim();
                signup();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void signup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(SignUp.this, ""+FirebaseAuth.getInstance().getUid(), Toast.LENGTH_SHORT).show();
                UserModel userModel=new UserModel(FirebaseAuth.getInstance().getUid(),Username,Email,Password);
                databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println(task);
                    }
                });
                startActivity(new Intent(SignUp.this,MainActivity.class));
                finish();
            }
        });
    }


}