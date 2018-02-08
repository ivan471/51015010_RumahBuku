package com.example.user.rumahbuku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    User user;
    EditText nama,nohp,pass,lib;
    CardView register;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nama=(EditText)findViewById(R.id.etnama);
        lib=(EditText)findViewById(R.id.etnamalib);
        nohp=(EditText)findViewById(R.id.etnomor);
        pass=(EditText)findViewById(R.id.etpass);
        register=(CardView) findViewById(R.id.btnreg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nama.getText().toString().length()==0){
                    nama.setError("Kosong!");
                }else if(lib.getText().toString().length()==0){
                    lib.setError("Kosong!");
                }else if(nohp.getText().toString().length()==0){
                    nohp.setError("Kosong!");
                }else if(pass.getText().toString().length()==0){
                    pass.setError("Kosong!");
                }else if(pass.getText().toString().length()<=6){
                    pass.setError("Minimal 6 digit!");
                }else if(pass.getText().toString().length()<=6){
                    pass.setError("Minimal 6 digit!");
                }else {
                    user = new User();
                    user.setNama(nama.getText().toString());
                    user.setNamalib(lib.getText().toString());
                    user.setTelepon(nohp.getText().toString());
                    user.setPassword(pass.getText().toString());
                    user.register();
                    Toast.makeText(getApplicationContext(), "Register Sukses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }
        });
    }
}
