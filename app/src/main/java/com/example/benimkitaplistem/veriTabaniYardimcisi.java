package com.example.benimkitaplistem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/*
veriTabaniYardimcisi sınıfı yalnızca "Kitaplar" adındaki bir veritabanını kontrol eder ve yönetir.
 Veritabanı yardımcısı sınıfı, genellikle tek bir veritabanı için kullanılır
 */

public class veriTabaniYardimcisi extends SQLiteOpenHelper {
    private static final String VERITABANI_ADI = "Kitaplar";
    //uygulamanın ilk sürümünde veritabanı versiyonu 1 olarak başlatılır ve ardından uygulamanın gelişimi ve değişiklikleriyle birlikte versiyon numarası artırılır. Bu, veritabanı yapılarını yönetmek ve güncellemek için daha iyi bir kontrol sağlar.
    private static final int VERITABANI_VERSION = 1;

    public veriTabaniYardimcisi(Context context) {
        // Üst sınıfın kurucu metodunu çağırarak veritabanı oluşturulur
        super(context, VERITABANI_ADI, null, VERITABANI_VERSION);
    }

    @Override
    // Veritabanı oluşturulduğunda çağrılan metot
    public void onCreate(SQLiteDatabase db) {
        // Kitaplar tablosunu oluşturan SQL komutu
        db.execSQL("CREATE TABLE IF NOT EXISTS kitaplar(id INTEGER PRIMARY KEY, kitapAdi VARCHAR, kitapYazari VARCHAR, kitapOzeti VARCHAR, kitapResim BLOB)");
    }

    @Override
    /*
    Bu yöntemde, mevcut kitaplar tablosu kontrol edilir. Eğer bu tablo zaten varsa, mevcut tablo silinir (DROP TABLE IF EXISTS kitaplar) ve yeniden oluşturulur
     (onCreate(db)). Böylece, herhangi bir şemadaki değişiklikler uygulanmış olur ve güncellenmiş veritabanı şeması kullanılmaya başlanır.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kitaplar");
        onCreate(db);
    }

    public void kitapSil(String kitapAdi) {
        // Yazılabilir veritabanı erişimi sağlanır
        SQLiteDatabase db = this.getWritableDatabase();
        // Veritabanından belirtilen kitabı silen SQL komutu
        db.delete("kitaplar", "kitapAdi=?", new String[]{kitapAdi});
        db.close();
    }

    public void kitapEkle(String kitapAdi, String kitapYazari, String kitapOzeti, byte[] kitapResim) {
        //getWritableDatabase() yöntemi, veritabanına yazma izni verir ve bu nedenle veritabanına veri eklemek veya güncellemek için kullanılır. Bu örneği db adında bir değişkende saklarız.
        //Bu satırlar, veritabanına bir SQL INSERT sorgusu göndermek için gerekli olan işlemleri gerçekleştirir.
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlSorgusu = "INSERT INTO kitaplar(kitapAdi, kitapYazari, kitapOzeti, kitapResim) VALUES(?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sqlSorgusu);
        statement.bindString(1, kitapAdi);
        statement.bindString(2, kitapYazari);
        statement.bindString(3, kitapOzeti);
        statement.bindBlob(4, kitapResim);
        statement.executeInsert();
        db.close();
    }

    //kitapEkle methodunu bu şekilde degistirebiliriz herhangi bir sqlKomutu kullanmamıza gerek kalmadan
    public void basitEkleme(String kitapAdi, String kitapYazari, String kitapOzeti, byte[] kitapResim){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("kitapAdi", kitapAdi);
        values.put("kitapYazari", kitapYazari);
        values.put("kitapOzeti", kitapOzeti);
        values.put("kitapResim", kitapResim);
        db.insert("kitaplar", null, values);
        db.close();
    }
}
