package com.firejobcourse.apps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePendaftaranAct extends AppCompatActivity   {


    TextView username_profile,password_profile,email_profile;



    String _USERNAME,_EMAIL,_PASSWORD;

    DatabaseReference reference;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pendaftaran);

        username_profile = (TextView) findViewById(R.id.usernamelabel);
        password_profile = (TextView) findViewById(R.id.passwordlabel);
        email_profile = (TextView) findViewById(R.id.emaillabel);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("ekasakti");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String usernamefromdb = snapshot.child("username").getValue().toString();
                    String emailfromdb = snapshot.child("email_address").getValue().toString();
                    String passwordfromdb = snapshot.child("password").getValue().toString();

                    username_profile.setText(usernamefromdb);
                    password_profile.setText(passwordfromdb);
                    email_profile.setText(emailfromdb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();

            }
        });



    }


//    public void update(View view){
//        if(isUsernameChanged() || isPasswordChanged() || isEmailChanged()){
//            Toast.makeText(this, "Perubahan Data Berhasil", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(this, "Tidak Ada Perubahan Data", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private boolean isEmailChanged() {
//        if (!_PASSWORD.equals(passwordLabel.getText().toString())){
//            reference.child(_PASSWORD).child("password").setValue(passwordLabel.getText().toString());
//            _PASSWORD = passwordLabel.getText().toString();
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    private boolean isPasswordChanged() {
//        if (!_PASSWORD.equals(passwordLabel.getText().toString())){
//            reference.child(_PASSWORD).child("password").setValue(passwordLabel.getText().toString());
//            _PASSWORD = passwordLabel.getText().toString();
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    private boolean isUsernameChanged() {
//
//        if (!_USERNAME.equals(usernameLabel.getText().toString())){
//            reference.child(_USERNAME).child("username").setValue(usernameLabel.getText().toString());
//            _USERNAME = usernameLabel.getText().toString();
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public void back(View view){
        Intent intent = new Intent(UpdatePendaftaranAct.this, HomeAct.class);
        startActivity(intent);
    }
}
