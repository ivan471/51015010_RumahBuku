package com.example.user.rumahbuku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference bukuRef = database.getReference("Books");
    private RecyclerView recyclerView;
    private List<Buku> bukus;
    private RecyclerView.Adapter adapter;
    User user;
    SharedPreferences mylocaldata;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
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
        recyclerView = (RecyclerView) findViewById(R.id.rvbuku);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        bukus = new ArrayList<>();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        bukuRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        bukuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Buku buku  = postSnapshot.getValue(Buku.class);
                    bukus.add(buku);
                }
                adapter = new BukuListAdapter(getApplicationContext(), bukus);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
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
