package com.example.user.rumahbuku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private User user;
    EditText etnama,nohp,pass,lib,e_mail;
    private CardView register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    TextView test;
    String userid;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
        progressDialog = new ProgressDialog(this);
        etnama=(EditText)findViewById(R.id.etnama);
        e_mail=(EditText)findViewById(R.id.et_email);
        lib=(EditText)findViewById(R.id.etnamalib);
        nohp=(EditText)findViewById(R.id.etnomor);
        pass=(EditText)findViewById(R.id.editpass);
        register=(CardView) findViewById(R.id.btnreg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = e_mail.getText().toString().trim();
                final String password  = pass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }else if (pass.getText().toString().length()<6){
                    pass.setError("Minimal 6 digit!");
                }else if (TextUtils.isEmpty(nohp.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Please enter No.Hp",Toast.LENGTH_LONG).show();
                    return;
                }else if (TextUtils.isEmpty(etnama.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Please enter nama",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    progressDialog.setMessage("Registering Please Wait...");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //checking if success
                                    if(task.isSuccessful()){
                                        userid = firebaseAuth.getCurrentUser().getUid();
                                        user = new User();
                                        user.setNama(etnama.getText().toString());
                                        user.setNamalib(lib.getText().toString());
                                        user.setTelepon(nohp.getText().toString());
                                        userRef.child(userid).setValue(user);
                                        Toast.makeText(RegisterActivity.this,"Registration Success",Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }else{
                                        //display some message here
                                        Toast.makeText(RegisterActivity.this,"Registration Error,E-Mail Sudah Digunakan",Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });

                }
            }
        });

    }

}
