package com.example.benimkitaplistem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;



/*                                                                            ---OZET---
getData() metodu, veritabanı bağlantısını oluşturur ve "Kitaplar" adlı bir veritabanı oluşturur veya varsa ona erişir. Daha sonra "kitaplar" tablosundan tüm verileri seçmek için bir sorgu çalıştırır.

Cursor, sorgu sonuçlarını alır ve her bir sıra için ilgili sütunların dizinlerini alır (kitapAdiIndex, kitapYazariIndex, kitapOzetiIndex, kitapResimIndex).

Daha sonra, while döngüsü içinde, her satırın sütun değerlerini alarak bunları ilgili ArrayList'lere ekler. Bu adımda, kitap resimleri byte dizisi olarak veritabanında saklanır ve bu byte dizisi BitmapFactory.decodeByteArray() yöntemi kullanılarak geri dönüştürülerek Bitmap'e dönüştürülür ve sonra ArrayList'e eklenir.

Son olarak, tüm kitap bilgileri ArrayList'lerde toplandıktan sonra, her bir kitap için bir Kitap nesnesi oluşturulur ve bu nesneler de bir kitapList ArrayList'ine eklenir.

 */

public class Kitap {
    private String kitapAdi,kitapYazari,kitapOzeti;

    private Bitmap kitapResim;

    static public ArrayList<Kitap> getData(Context context){//aktiviteyi aliriz iceriden
        //Bu satırlar, kitap verilerini depolamak için kullanılacak ArrayList'leri tanımlar.
        ArrayList<Kitap> kitapList= new ArrayList<>();
        ArrayList<String> kitapAdiList=new ArrayList<>();
        ArrayList<String> kitapYazariList=new ArrayList<>();
        ArrayList<String> kitapOzetiList=new ArrayList<>();
        ArrayList<Bitmap> kitapResimList=new ArrayList<>();

        try{
            // Veritabanı bağlantısı oluşturulur veya varsa var olan bağlantı alınır.
            SQLiteDatabase database = context.openOrCreateDatabase("Kitaplar",Context.MODE_PRIVATE,null);
//Bu satır, kitaplar tablosundaki tüm verileri almak için bir sorgu çalıştırır ve sonuçlarını bir Cursor nesnesine yerleştirir.
            Cursor cursor=database.rawQuery("SELECT*FROM kitaplar",null); //null== herhangi bir filtre yoktur demek


            // Bu kısım, Cursor nesnesinin içinde bulunduğu sorgu sonucunda dönen veri kümesinde, "kitapAdi" adında bir sütunun indeksini alır. Yani, bu metot ile "kitapAdi" sütununun hangi sıra numarasında olduğunu öğreniriz.
            // Bu indeks, daha sonra getString() veya benzeri metotlarla sütunun değerini almak için kullanılacaktır.

            //Bu indeks, sorgu sonucunda dönen satırların her birindeki belirli bir sütunun indeksidir. Dolayısıyla, bu indeks, sütunun yukarıdan aşağıya doğru sıralanmasıyla belirlenir.
            int kitapAdiIndex=cursor.getColumnIndex("kitapAdi");
            int kitapYazariIndex=cursor.getColumnIndex("kitapYazari");
            int kitapOzetiIndex=cursor.getColumnIndex("kitapOzeti");
            int kitapResimIndex=cursor.getColumnIndex("kitapResim");

            while(cursor.moveToNext()){ //her bir indexin(satırın) bir sutununu listeye ekledikten sonra diger indexin sutunlarını tarar(tablo gibi dusun)
//bu verileri arraylistlere yukleyerek her defasında veri tabanından sorgu almamızı onler
                kitapAdiList.add(cursor.getString(kitapAdiIndex));
                kitapYazariList.add(cursor.getString(kitapYazariIndex));
                kitapOzetiList.add(cursor.getString(kitapOzetiIndex));

                byte[] gelenResimByte=cursor.getBlob(kitapResimIndex);
                Bitmap gelenResim= BitmapFactory.decodeByteArray(gelenResimByte,0, gelenResimByte.length);
                kitapResimList.add(gelenResim);
            }
            cursor.close();

            for(int i=0;i<kitapAdiList.size();i++){
                Kitap kitap=new Kitap();
                kitap.setKitapAdi(kitapAdiList.get(i));
                kitap.setKitapOzeti(kitapOzetiList.get(i));
                kitap.setKitapYazari(kitapYazariList.get(i));
                kitap.setKitapResim(kitapResimList.get(i));

                kitapList.add(kitap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return kitapList;
    }

    public Kitap(){}



    public Kitap(String kitapAdi, String kitapYazari, String kitapOzeti, Bitmap kitapResim) { //bu constructor'i kullanip isi daha'da rahatlastirabilirdim fakat daha net anlamak icin  kullanmadım
        this.kitapAdi = kitapAdi;
        this.kitapYazari = kitapYazari;
        this.kitapOzeti = kitapOzeti;
        this.kitapResim = kitapResim;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getKitapYazari() {
        return kitapYazari;
    }

    public void setKitapYazari(String kitapYazari) {
        this.kitapYazari = kitapYazari;
    }

    public String getKitapOzeti() {
        return kitapOzeti;
    }

    public void setKitapOzeti(String kitapOzeti) {
        this.kitapOzeti = kitapOzeti;
    }

    public Bitmap getKitapResim() {
        return kitapResim;
    }

    public void setKitapResim(Bitmap kitapResim) {
        this.kitapResim = kitapResim;
    }
}
