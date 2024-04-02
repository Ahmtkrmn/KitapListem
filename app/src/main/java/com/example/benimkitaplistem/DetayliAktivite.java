package com.example.benimkitaplistem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetayliAktivite extends AppCompatActivity {

    private ImageView imgKitapResim;
    private ImageButton deleteButton;
    private TextView txtKitapAdi,txtKitapYazari,txtKitapOzeti;
    private String kitapAdi,kitapYazari,kitapOzeti;
    private Bitmap kitapResim;

    private void init(){
        imgKitapResim=(ImageView) findViewById(R.id.detayAktiviteResim);
        txtKitapAdi=(TextView) findViewById(R.id.detayAktiviteKitapAdi);
        txtKitapYazari=(TextView) findViewById(R.id.detayAktiviteKitapYazari);
        txtKitapOzeti=(TextView) findViewById(R.id.detayAktiviteKitapOzeti);
        deleteButton=(ImageButton)findViewById(R.id.deleteButton);

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
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);

        if(!TextUtils.isEmpty(kitapAdi) && !TextUtils.isEmpty(kitapYazari) && !TextUtils.isEmpty(kitapOzeti)){ // burası nın dogru olması kesin fakat RAM 'den dolayı veya herhangi bir ariza olursa diye
            txtKitapAdi.setText(kitapAdi);
            txtKitapYazari.setText(kitapYazari);
            txtKitapOzeti.setText(kitapOzeti);
            imgKitapResim.setImageBitmap(kitapResim);

        }else {
            System.out.println("Bir Hata Oluştu");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Silinecek kitabın ID'sini alın
                String silinecekKitapAdi = MainActivity.kitapDetayi.getKitapAdi(); // Varsayılan olarak ID alındı, sizin uygulamanıza göre bu kısmı uyarlamanız gerekebilir

                // Veritabanı yardımcısını oluştur
                veriTabaniYardimcisi dbHelper = new veriTabaniYardimcisi(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Kitap Başarıyla Silinmiştir", Toast.LENGTH_SHORT).show();

                // Kitabı sil
                dbHelper.kitapSil(silinecekKitapAdi);



            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out); // Geri dönüş animasyonu
    }




}