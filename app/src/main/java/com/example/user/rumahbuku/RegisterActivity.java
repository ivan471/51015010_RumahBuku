package com.example.user.rumahbuku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    User user;
    EditText nama,nohp,pass,lib;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nama=(EditText)findViewById(R.id.etnama);
        lib=(EditText)findViewById(R.id.etnamalib);
        nohp=(EditText)findViewById(R.id.etnomor);
        pass=(EditText)findViewById(R.id.etpass);
        register=(Button) findViewById(R.id.btsignup);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }
}
