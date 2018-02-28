package com.example.user.rumahbuku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String userid;
    SharedPreferences mylocaldata;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameuser";
    public static final String Phone = "nohp";
    public static final String Lib = "namalib";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.etemail);
        editTextPassword = (EditText) findViewById(R.id.etpass);
        buttonSignIn = (CardView) findViewById(R.id.btnlogin);
        textViewSignup  = (TextView) findViewById(R.id.tvregister);
        progressDialog = new ProgressDialog(this);
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }else{
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            userid = firebaseAuth.getCurrentUser().getUid();
                            userRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mylocaldata = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                    User user = new User();
                                    if( dataSnapshot.exists() ){
                                        user.setNama(dataSnapshot.child("nama").getValue(String.class) );
                                        user.setTelepon(dataSnapshot.child("telepon").getValue(String.class) );
                                        user.setNamalib(dataSnapshot.child("namalib").getValue(String.class) );
                                        SharedPreferences.Editor editor = mylocaldata.edit();
                                        editor.putString(Name,user.getNama() );
                                        editor.putString(Phone,user.getTelepon() );
                                        editor.putString(Lib,user.getNamalib() );
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("user",user);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Login Gagal Email Atau Password Salah",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }
        if(view == textViewSignup){
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}