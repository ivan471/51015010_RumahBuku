package com.example.user.rumahbuku;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by USER on 26-Jan-18.
 */
@IgnoreExtraProperties
public class Buku implements Parcelable {
    private String id_buku;
    private String penulis_buku;
    private String penerbit_buku;
    private String judul_buku;
    private String url;
    private String namauser;
    private String namalib;

    public String getNamauser() {
        return namauser;
    }

    public void setNamauser(String namauser) {
        this.namauser = namauser;
    }

    public String getNamalib() {
        return namalib;
    }

    public void setNamalib(String namalib) {
        this.namalib = namalib;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_buku() {
        return id_buku;
    }

    public void setId_buku(String id_buku) {
        this.id_buku = id_buku;
    }

    public String getPenulis_buku() {
        return penulis_buku;
    }

    public void setPenulis_buku(String penulis_buku) {
        this.penulis_buku = penulis_buku;
    }

    public String getPenerbit_buku() {
        return penerbit_buku;
    }

    public void setPenerbit_buku(String penerbit_buku) {
        this.penerbit_buku = penerbit_buku;
    }

    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public Buku() {
    }
    public Buku(String judul_buku, String penerbit_buku, String penulis_buku, String url) {
        this.judul_buku=judul_buku;
        this.penerbit_buku=penerbit_buku;
        this.penulis_buku=penulis_buku;
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_buku);
        dest.writeString(this.penulis_buku);
        dest.writeString(this.penerbit_buku);
        dest.writeString(this.judul_buku);
        dest.writeString(this.url);
        dest.writeString(this.namauser);
        dest.writeString(this.namalib);
    }

    protected Buku(Parcel in) {
        this.id_buku = in.readString();
        this.penulis_buku = in.readString();
        this.penerbit_buku = in.readString();
        this.judul_buku = in.readString();
        this.url = in.readString();
        this.namauser = in.readString();
        this.namalib = in.readString();
    }

    public static final Creator<Buku> CREATOR = new Creator<Buku>() {
        @Override
        public Buku createFromParcel(Parcel source) {
            return new Buku(source);
        }

        @Override
        public Buku[] newArray(int size) {
            return new Buku[size];
        }
    };
}
