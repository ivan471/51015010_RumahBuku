package com.example.user.rumahbuku;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadBukuActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    private ImageView imageView;
    private Button btnpilih;
    private EditText etjudul,penerbit,penulis;
    private CardView btnupload;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    User user;
    SharedPreferences mylocaldata;
    FirebaseAuth firebaseAuth;
    String users,libs;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameuser";
    public static final String Lib = "namalib";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_buku);
        imageView = (ImageView) findViewById(R.id.imageview);
        etjudul = (EditText) findViewById(R.id.etjudul);
        penerbit = (EditText) findViewById(R.id.etpenerbit);
        penulis = (EditText) findViewById(R.id.etpenulis);
        btnpilih = (Button) findViewById(R.id.btnpilih);
        btnupload = (CardView) findViewById(R.id.btnupload);
        firebaseAuth = FirebaseAuth.getInstance();
        mylocaldata = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        users= mylocaldata.getString(Name,"");
        libs= mylocaldata.getString(Lib,"");
        user = getIntent().getParcelableExtra("user");
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        btnpilih.setOnClickListener(this);
        btnupload.setOnClickListener(this);
    }
    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));
            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            Buku buku = new Buku(etjudul.getText().toString().trim(),penerbit.getText().toString().trim(),penulis.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString(),users,libs);
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(buku);
                            finish();
                            startActivity(new Intent(UploadBukuActivity.this, MainActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnpilih) {
            showFileChooser();
        } else if (view == btnupload) {
            uploadFile();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuuploadbuku) {
            Intent intent = new Intent(UploadBukuActivity.this, UploadBukuActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }else if (item.getItemId()==R.id.menuprofil) {
            startActivity(new Intent(UploadBukuActivity.this, ProfilActivity.class));
        }else if (item.getItemId()==R.id.menuhome){
            startActivity(new Intent(UploadBukuActivity.this, MainActivity.class));
        }else if (item.getItemId()==R.id.menuLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(UploadBukuActivity.this, LoginActivity.class));
        }
        return true;
    }
}
