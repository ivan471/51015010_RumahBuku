package com.example.user.rumahbuku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    private TextView tvreg;
    private EditText etno,etpass;
    private Button btLogin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    SharedPreferences mylocaldata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvreg = (TextView) findViewById(R.id.tvregister);
        etno = (EditText) findViewById(R.id.ethp);
        etpass = (EditText) findViewById(R.id.etpass);
        btLogin = (Button) findViewById(R.id.btnlogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomor = etno.getText().toString();
                userRef.child( nomor ).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mylocaldata = getSharedPreferences("mylocaldata", MODE_PRIVATE);
                        User user = new User();
                        if( dataSnapshot.exists() ){
                            user.setNama(dataSnapshot.child("nama").getValue(String.class) );
                            user.setNamalib(dataSnapshot.child("nama perpustakaan").getValue(String.class) );
                            user.setTelepon(dataSnapshot.child("telepon").getValue(String.class) );
                            SharedPreferences.Editor editor = mylocaldata.edit();
                            editor.putString("uid", user.getTelepon() );
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "User tidak ditemukan";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
