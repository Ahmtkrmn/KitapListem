package com.example.benimkitaplistem;

import android.graphics.Bitmap;

public class KitapDetayi {

    private String kitapAdi,kitapYazari,kitapOzeti;
    private Bitmap kitapResim;

    public KitapDetayi(String kitapAdi, String kitapYazari, String kitapOzeti, Bitmap kitapResim) {
        this.kitapAdi = kitapAdi;
        this.kitapYazari = kitapYazari;
        this.kitapOzeti = kitapOzeti;
        this.kitapResim = kitapResim;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public String getKitapYazari() {
        return kitapYazari;
    }

    public String getKitapOzeti() {
        return kitapOzeti;
    }

    public Bitmap getKitapResim() {
        return kitapResim;
    }
}
