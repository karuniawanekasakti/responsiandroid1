package com.firejobcourse.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeAct extends AppCompatActivity {

    ImageView btn_profile, btn_edit, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_profile = findViewById(R.id.btn_profile);
        btn_edit = findViewById(R.id.btn_edit);
        btn_logout = findViewById(R.id.btn_logout);



    }

    public void data_pendaftaran(View view){
        Intent intent = new Intent(HomeAct.this, UpdatePendaftaranAct.class);
        startActivity(intent);
    }

    public void data_profile(View view){
        Intent intent = new Intent(HomeAct.this, UpdateProfileAct.class);
        startActivity(intent);
    }

    public void logout_act(View view){
        Intent intent = new Intent(HomeAct.this, SuccessLogoutAct.class);
        startActivity(intent);
    }


}

