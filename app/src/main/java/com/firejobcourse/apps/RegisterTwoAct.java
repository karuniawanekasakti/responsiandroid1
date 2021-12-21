package com.firejobcourse.apps;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterTwoAct extends AppCompatActivity {

    Button btn_continue, btn_add_photo;
    LinearLayout btn_back;
    EditText bio, nama_lengkap;
    ImageView pic_photo_register;

    Uri photo_location;
    Integer photo_max=1;

    String USERNAME_KEY= "usernamekey";
    String username_key="";
    String username_key_new="";

    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal(); //memanggil method data username local

        bio = findViewById(R.id.bio);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        btn_back=findViewById(R.id.btn_back);
        pic_photo_register=findViewById(R.id.pic_photo_register_user);
        btn_continue=findViewById(R.id.btn_continue);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               findPhoto(); // method untuk mengambil data foto di lokal
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ubah state menjadi loading
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading....");

                //menyimpan file di database

                reference=FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage=FirebaseStorage.getInstance().getReference().child("Photouser").child(username_key_new);

                //validasi file

                if(photo_location!= null){
                    StorageReference storageReference = storage.child(System.currentTimeMillis()+"."+
                            getFileExtension(photo_location));
                    storageReference.putFile(photo_location).
                            addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri_photo = taskSnapshot.getMetadata().
                                    getReference().getDownloadUrl().toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                            reference.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                            reference.getRef().child("nik").setValue(bio.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotosuccessregister= new Intent(
                                    RegisterTwoAct.this, SuccessRegisterAct.class);
                            startActivity(gotosuccessregister);
                        }
                    });
                }


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backtoregiter1= new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(backtoregiter1);
            }
        });

    }

String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
}
    
    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){

            photo_location= data.getData();

            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_register);
        }

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences=getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
    }
}
