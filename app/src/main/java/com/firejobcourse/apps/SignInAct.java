package com.firejobcourse.apps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignInAct extends AppCompatActivity {
    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername,xpassword;
    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_new_account=findViewById(R.id.btn_new_account_create);
        btn_sign_in=findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    // ubah state menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }
                else {
                    if(password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        // ubah state menjadi loading
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    // ambil data password dari firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    // validasi password dengan password firebase
                                    if(password.equals(passwordFromFirebase)){

                                        String emailFromFirebase = dataSnapshot.child(password).child("email_addres").getValue(String.class);
                                        String namaFromFirebase = dataSnapshot.child(password).child("nama_lengkap").getValue(String.class);
                                        String bioFromFirebase = dataSnapshot.child(password).child("nik").getValue(String.class);
                                        String userFromFirebase = dataSnapshot.child(password).child("username").getValue(String.class);



                                        // simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();


                                        // berpindah activity
                                        getIntent().putExtra("email_address",emailFromFirebase);
                                        getIntent().putExtra("nama_lengkap",namaFromFirebase);
                                        getIntent().putExtra("nik",bioFromFirebase);
                                        getIntent().putExtra("username",userFromFirebase);
                                        getIntent().putExtra("password",passwordFromFirebase);
                                        Intent gotohome = new Intent(SignInAct.this,HomeAct.class);



                                        startActivity(gotohome);

                                    }
                                    //toast pasword salah
                                  else{
                                        Toast.makeText(getApplicationContext(), "Password atau Username salah!", Toast.LENGTH_SHORT).show();
                                        // ubah state menjadi loading
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");

                                    }


                                }
                                //toast username tidak ada

                                else {
                                    Toast.makeText(getApplicationContext(), "Password atau Username Tidak Ada!", Toast.LENGTH_SHORT).show();
                                    // ubah state menjadi loading
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }

        });


//        btn_new_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent gotoregister= new Intent(SignInAct.this, RegisterOneAct.class);
//                startActivity(gotoregister);
//            }
//        });


    }
}
