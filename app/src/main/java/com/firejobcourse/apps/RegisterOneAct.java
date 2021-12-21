package com.firejobcourse.apps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterOneAct extends AppCompatActivity {

    Button btn_continoue;
    LinearLayout btn_back;
    EditText username, password, email_address;
    String USERNAME_KEY= "usernamekey";
    String username_key="";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        btn_continoue=findViewById(R.id.btn_continue);

        btn_continoue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan data local storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(username_key,username.getText().toString());
                editor.apply();

                //untuk menyimpan data di firebase

                reference = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(username.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("username").setValue(username.getText().toString());
                        snapshot.getRef().child("password").setValue(password.getText().toString());
                        snapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent gotoregister2= new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotoregister2);
            }
        });

            btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotosignin= new Intent(RegisterOneAct.this, SignInAct.class);
                startActivity(gotosignin);
            }
        });


    }

}
