package com.example.user.rumahbuku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    User user;
    SharedPreferences mylocaldata;
    FirebaseAuth firebaseAuth;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameuser";
    public static final String Phone = "nohp";
    public static final String Lib = "namalib";
    public String users,libs,hp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylocaldata = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        user = getIntent().getParcelableExtra("user");
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        loaddata();
    }
    public void loaddata(){
        users= mylocaldata.getString(Name,"");
        libs= mylocaldata.getString(Lib,"");
        hp= mylocaldata.getString(Phone,"");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuuploadbuku) {
            Intent intent = new Intent(MainActivity.this, UploadBukuActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }else if (item.getItemId()==R.id.menuLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return true;
    }
}
