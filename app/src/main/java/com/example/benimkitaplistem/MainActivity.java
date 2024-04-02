package com.example.benimkitaplistem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

/*
Bu sınıf, KitapAdapter ve Kitap sınıfları ile birlikte çalışarak, kitap verilerinin alınmasını, görüntülenmesini ve kullanıcıya yeni kitap eklenmesini sağlar.
 Ayrıca, menü öğeleri üzerinden kullanıcı etkileşimini yönetir ve ilgili aktivitelere geçiş yapar.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private KitapAdapter adapter;
    static public KitapDetayi kitapDetayi;

    @Override
    //onCreateOptionsMenu yöntemi, menü öğelerini Toolbar'a eklemenize olanak tanırken,
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu); //bizi menuye yonlendirir
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //kullanıcının tikladigi menu ifadesinde gerceklesecek olan islemleri ele aliriz
        if(item.getItemId()==R.id.ad_book){ //eger + ikonuna bastiysa
            //Intent geçiş
            Intent addBookIntent=new Intent(this,AddBookActivity.class);//bu aktiviteden kitap ekleye gecisi saglar
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            startActivity(addBookIntent);
        }
        return super.onOptionsItemSelected(item);//Üst sınıfın (genellikle Activity) normal işlem akışını devam ettir.
    }

    @Override
    protected void onResume() {
        // Buraya aktivitenin yeniden etkinleştirildiği zaman gerçekleştirilmesi gereken işlemler yazılır
        super.onResume();
        adapter = new KitapAdapter(Kitap.getData(this), this);//kitaplistesindeki kitaplarin verilerini alir
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new KitapAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Kitap kitap) {
//adapterdaki tiklanan kitabin bilgileri aktarilir ardindan bunun icin bir sinif olusturulur ve bu sinif o bilgilerin detayliaktivitede yazmasini saglar
            kitapDetayi=new KitapDetayi(kitap.getKitapAdi(), kitap.getKitapYazari(), kitap.getKitapOzeti(), kitap.getKitapResim());
            Intent detayIntent=new Intent(MainActivity.this, DetayliAktivite.class);
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                startActivity(detayIntent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar); //toolbarı action bar olarak ayarladik
        setSupportActionBar(toolbar); //ve aktiflestirdik

        mRecyclerView=(RecyclerView) findViewById(R.id.recyleViewMain);

        //İlk this, KitapAdapter'ın kurucu metoduna geçirilen kitaplist parametresi için kullanılırken, ikinci this, Context parametresi için kullanılır
        //context parametresi  kaynaklarına erişim, aktiviteler arası geçişler ve diğer sistem düzeyi işlemler için gereklidir.
        adapter= new KitapAdapter(Kitap.getData(this),this);//burada recyle view 'in icini doldurmak icin kitap verilerini adaptera yolladik,kitapadapter sınıfı bu gorunumun saglanmasını saglar


        mRecyclerView.setHasFixedSize(true);
       // LinearLayoutManager manager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager manager=new GridLayoutManager(this,1); //kolon sayısı vererek bir satırda kaç tane olacagınıda yapabilirizi
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridManagerDecoration());
        //mRecyclerView.setAdapter(adapter): RecyclerView'a bir adaptör (KitapAdapter) atar, böylece kitap verileri görüntülenebilir hale gelir
        mRecyclerView.setAdapter(adapter);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);



    }
    private class GridManagerDecoration extends RecyclerView.ItemDecoration{ //kitaplar arasindaki bosluklarin buyuklugunu saglar
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom=15;
        }
    }
}