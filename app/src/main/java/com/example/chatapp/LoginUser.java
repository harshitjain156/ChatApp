package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser extends AppCompatActivity {
    EditText username,email,password;
    Button login;
    TextView signup;
    String Email,Username,Password;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
//        username=findViewById(R.id.);
        email=findViewById(R.id.SignInEmail);
        password=findViewById(R.id.SignInPassword);
        login=findViewById(R.id.SignInButton);
        signup=findViewById(R.id.SignInToSignUp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString();
//                Username=username.getText().toString();
                Password=password.getText().toString();
                login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginUser.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

//    private void signUp() {
//        FirebaseAuth
//                .getInstance()
//                .signInWithEmailAndPassword(Email.trim(),Password)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Toast.makeText(LoginUser.this, ""+authResult.toString(), Toast.LENGTH_SHORT).show();
//
//                        UserModel userModel=new UserModel(FirebaseAuth.getInstance().getUid(),Username,Email,Password);
//                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
//                        startActivity(new Intent(LoginUser.this,MainActivity.class));
//                        finish();
//                    }
//                });
//
//
//    }

    private void login() {
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(Email.trim(),Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginUser.this, ""+authResult.toString(), Toast.LENGTH_SHORT).show();
                        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(Username).build();
                        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        startActivity(new Intent(LoginUser.this,MainActivity.class));
                        finish();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginUser.this,MainActivity.class));
            finish();
        }
    }
}