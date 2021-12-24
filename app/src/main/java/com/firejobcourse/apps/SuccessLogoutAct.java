package com.firejobcourse.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessLogoutAct extends AppCompatActivity {

    Button btn_login;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_logout);

        btn_login=findViewById(R.id.btn_login);


    }

    public void login(View view){
        Intent intent = new Intent(SuccessLogoutAct.this, SignInAct.class);
        startActivity(intent);
    }
}
