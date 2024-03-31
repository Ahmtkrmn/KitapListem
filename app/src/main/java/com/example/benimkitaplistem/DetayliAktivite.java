package com.example.benimkitaplistem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DetayliAktivite extends AppCompatActivity {

    private ImageView imgKitapResim;
    private TextView txtKitapAdi,txtKitapYazari,txtKitapOzeti;
    private String kitapAdi,kitapYazari,kitapOzeti;
    private Bitmap kitapResim;

    private void init(){
        imgKitapResim=(ImageView) findViewById(R.id.detayAktiviteResim);
        txtKitapAdi=(TextView) findViewById(R.id.detayAktiviteKitapAdi);
        txtKitapYazari=(TextView) findViewById(R.id.detayAktiviteKitapYazari);
        txtKitapOzeti=(TextView) findViewById(R.id.detayAktiviteKitapOzeti);

        kitapAdi=MainActivity.kitapDetayi.getKitapAdi();
        kitapYazari=MainActivity.kitapDetayi.getKitapYazari();
        kitapOzeti=MainActivity.kitapDetayi.getKitapOzeti();
        kitapResim=MainActivity.kitapDetayi.getKitapResim();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detayli_aktivite);
        init();

        if(!TextUtils.isEmpty(kitapAdi) && !TextUtils.isEmpty(kitapYazari) && !TextUtils.isEmpty(kitapOzeti)){ // burası nın dogru olması kesin fakat RAM 'den dolayı veya herhangi bir ariza olursa diye
            txtKitapAdi.setText(kitapAdi);
            txtKitapYazari.setText(kitapYazari);
            txtKitapOzeti.setText(kitapOzeti);
            imgKitapResim.setImageBitmap(kitapResim);

        }else {
            System.out.println("Bir Hata Oluştu");
        }


    }
}