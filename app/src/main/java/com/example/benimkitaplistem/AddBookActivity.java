package com.example.benimkitaplistem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddBookActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> resultLauncher;
    private veriTabaniYardimcisi veritabaniYardimcisi;


    private EditText editTxtKitapAdi, editTxtKitapYazari, editTxtKitapOzeti;
    private ImageView imgKitapResim;
    private String kitapAdi, kitapYazari, kitapOzeti;
    private Bitmap secilenResim,kucultulenResim,enBastakiResim;

    private void init() {
        editTxtKitapAdi = (EditText) findViewById(R.id.editTextKitapAdi);
        editTxtKitapYazari = (EditText) findViewById(R.id.editTextKitapYazari);
        editTxtKitapOzeti = (EditText) findViewById(R.id.editTextKitapOzeti);
        imgKitapResim = (ImageView) findViewById(R.id.ImageViewKitapResmi);
        veritabaniYardimcisi=new veriTabaniYardimcisi(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        init(); //on ve arka ucu bagladik
        registerResult();//gerekli hazirliklarin yapilmasini saglar




    }

    public void kitapKaydet(View v) {
        kitapAdi = editTxtKitapAdi.getText().toString();
        kitapYazari = editTxtKitapYazari.getText().toString();
        kitapOzeti = editTxtKitapOzeti.getText().toString();

        if (!TextUtils.isEmpty(kitapAdi)) {
            if (!TextUtils.isEmpty(kitapYazari)) {
                if (!TextUtils.isEmpty(kitapOzeti)) {
                    if(secilenResim!=null){
                        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
                        kucultulenResim=resimiKucult(secilenResim);
                        // Resmi sıkıştır ve byte dizisine dönüştür
                        if (kucultulenResim.compress(Bitmap.CompressFormat.PNG, 50, outputStream)) { // sıkıştırma başarılıysa devam et
                            byte[] kayitEdilecekResim = outputStream.toByteArray();


                            try {
                                //veritabanina veriTabaniYardimcisi class'i ile bir kitap ekleriz
                                veritabaniYardimcisi.kitapEkle(kitapAdi,kitapYazari,kitapOzeti,kayitEdilecekResim);
                                nesneleriTemizle(); //girilen alanları temizler
                                showToast("Kitap Başarıyla Kaydedilmiştir");



                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            showToast("Resim sıkıştırılamadı."); // sıkıştırma başarısızsa kullanıcıya bildir
                        }

                    }else
                        showToast("Lütfen Kitabınızın Resmini Seçiniz");




                } else
                    showToast("Lütfen Özet Bölümünü Doldurunuz");
            } else
                showToast("Yazar İsmi Boş Olamaz");
        } else
            showToast("Kitap İsmi Boş Olamaz");
    }

    private Bitmap resimiKucult(Bitmap resim){
        return Bitmap.createScaledBitmap(resim,125,150,true);//resimin boyutunu kucultuyoruz donma vs gibi seyler yasamamak icin
    }

    public void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();

    }

    private void nesneleriTemizle(){
        editTxtKitapAdi.setText("");
        editTxtKitapOzeti.setText("");
        editTxtKitapYazari.setText("");
        enBastakiResim= BitmapFactory.decodeResource(this.getResources(),R.drawable.img);
        imgKitapResim.setImageBitmap(enBastakiResim);
    }
private void registerResult() {
    resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                // onActivityResult() yöntemi, seçilen resmin URI'sini alır ve bu URI'yi kullanarak resmi ImageView'e yükler,
                // böylece kullanıcı seçtiği resmi görebilir.
                public void onActivityResult(ActivityResult result) {
                    //Bu iki koşul birlikte, kullanıcının bir resim seçtiği ve bu resmin verisinin null olmadığı durumu kontrol edilir
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        try {
                            // result.getData().getData() ifadesiyle, başlatılan aktivitenin sonucundan seçilen resmin URI'sini elde ederiz
                            Uri sectigimizResim = result.getData().getData();
                            imgKitapResim.setImageURI(sectigimizResim);
                            // Resmi bitmap olarak saklar (bu sayede kaydet methodunda bunu kaydedebiliriz
                            secilenResim = MediaStore.Images.Media.getBitmap(getContentResolver(), sectigimizResim);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Herhangi bir resim seçilmedi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );
}


    public void resimSec(View v){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //telefondaki dosyaları alıp getirmemizi saglar
        resultLauncher.launch(intent);
    }
    //Bu fonksiyon, kullanıcının bir resim seçmesini sağlamak için galeriyi açar.
    // Intent.ACTION_PICK eylemi kullanılarak galeri uygulaması başlatılır ve resultLauncher nesnesi kullanılarak başlatılan işlemin sonucu beklenir.
    // Kullanıcı bir resim seçtiğinde, bu seçilen resmin URI'si onActivityResult() yöntemine aktarılır ve orada işlenir.









}
