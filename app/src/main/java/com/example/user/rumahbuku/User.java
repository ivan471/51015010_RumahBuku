package com.example.user.rumahbuku;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by USER on 26-Jan-18.
 */

public class User implements Parcelable {
    private String id;
    private String nama;
    private String email;
    private String telepon;
    private String namalib;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamalib() {
        return namalib;
    }

    public void setNamalib(String namalib) {
        this.namalib = namalib;
    }


    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.email);
        dest.writeString(this.telepon);
        dest.writeString(this.namalib);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.email = in.readString();
        this.telepon = in.readString();
        this.namalib = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
