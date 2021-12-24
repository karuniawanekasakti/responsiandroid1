package com.firejobcourse.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileAct extends AppCompatActivity {

    TextView name_profile,bio_profile;

    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        name_profile = (TextView) findViewById(R.id.name_profile_edit);
        bio_profile = (TextView) findViewById(R.id.bio_profile_edit);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("ekasakti");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String namefromdb = snapshot.child("nama_lengkap").getValue().toString();
                    String biofromprofile = snapshot.child("nik").getValue().toString();

                    name_profile.setText(namefromdb);
                    bio_profile.setText(biofromprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void back(View view){
        Intent intent = new Intent(UpdateProfileAct.this, HomeAct.class);
        startActivity(intent);
    }

}
