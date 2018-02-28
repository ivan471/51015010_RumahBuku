package com.example.user.rumahbuku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameuser";
    public static final String Phone = "nohp";
    public static final String Lib = "namalib";
    public String users,libs,hp;
    SharedPreferences mylocaldata;
    private TextView tvnama,tvper,tvhp;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        firebaseAuth = FirebaseAuth.getInstance();
        mylocaldata = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tvnama=(TextView)findViewById(R.id.tvnama);
        tvper=(TextView)findViewById(R.id.tvper);
        tvhp=(TextView)findViewById(R.id.tvhp);
        users=mylocaldata.getString(Name,"");
        libs=mylocaldata.getString(Lib,"");
        hp=mylocaldata.getString(Phone,"");
        tvnama.setText(users);
        tvper.setText(libs);
        tvhp.setText(hp);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuhome) {
            Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (item.getItemId()==R.id.menuprofil) {
            Intent intent = new Intent(ProfilActivity.this, ProfilActivity.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.menuuploadbuku) {
            Intent intent = new Intent(ProfilActivity.this, UploadBukuActivity.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.menuLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
        }
        return true;
    }
}
