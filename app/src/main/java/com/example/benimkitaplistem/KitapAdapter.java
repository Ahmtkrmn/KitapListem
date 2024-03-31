package com.example.benimkitaplistem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
Bu kod dosyası bir RecyclerView için bir Adapter sınıfını tanımlar. RecyclerView, listedeki öğelerin verilerini dinamik olarak göstermek için kullanılır.
 Bu Adapter sınıfı, kitap verilerini RecyclerView'da görüntülemek için tasarlanmıştır
 */

public class KitapAdapter extends RecyclerView.Adapter<KitapAdapter.KitapHolder> {

    private ArrayList<Kitap> kitaplist;
    private Context context;
    private OnItemClickListener listener;

    public KitapAdapter(ArrayList<Kitap> kitaplist, Context context) {
        this.kitaplist = kitaplist;
        this.context = context;
    }

    @NonNull
    @Override
    //Bu metod, ViewHolder nesnelerini oluşturmak için çağrılır. Her bir öğe için bir ViewHolder oluşturulur ve ilgili düzen dosyası (kitap_item.xml) bu ViewHolder'a atanır.
    public KitapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kitap_item,parent,false); //burada gorunumu baglarız kitap item ile bir tane adapter'in
        return new KitapHolder(v);
    }

    @Override
    //Bu metod, verileri ViewHolder'a bağlamak için çağrılır. Belirli bir pozisyondaki kitap verileri, ViewHolder'a bağlanır ve görünümün güncellenmesi sağlanır
    public void onBindViewHolder(@NonNull KitapHolder holder, int position) {//ne kadar veri eklersek position degeri artacak
        Kitap kitap = kitaplist.get(position);
        holder.setData(kitap);

    }

    @Override
    // Bu metod, RecyclerView'da kaç öğe bulunduğunu döndürür. Bu durumda, kitap listesinin boyutunu döndürür.
    public int getItemCount() {
        return kitaplist.size();
    }

    class KitapHolder extends RecyclerView.ViewHolder  {

        TextView txtKitapAdi,txtKitapYazari,txtKitapOzeti;
        ImageView imgKitapResim;
        public KitapHolder(@NonNull View itemView) {
            // Bu metot, her bir öğenin görünümünü belirler. Kitap öğesinin bileşenleri burada tanımlanır.
            super(itemView);

            txtKitapAdi=(TextView)itemView.findViewById(R.id.kitap_item_txtViewKitapAdi);
            txtKitapYazari=(TextView)itemView.findViewById(R.id.kitap_item_txtViewKitapYazari);
            txtKitapOzeti=(TextView)itemView.findViewById(R.id.kitap_item_txtViewKitapOzeti);
            imgKitapResim=(ImageView) itemView.findViewById(R.id.kitap_item_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION){
                        listener.OnItemClick(kitaplist.get(position));
                    }
                }
            });
        }
        public void setData(Kitap kitap){//belirli bir indexteki kitap atanır ve ona ait bilgiler ekrana yazilir
            //Bu metot, bir Kitap nesnesini alır ve ilgili görünüme kitap verilerini atar. Bu, onBindViewHolder metodunda çağrılır.
            this.txtKitapAdi.setText(kitap.getKitapAdi());
            this.txtKitapYazari.setText(kitap.getKitapYazari());
            this.txtKitapOzeti.setText(kitap.getKitapOzeti());
            this.imgKitapResim.setImageBitmap(kitap.getKitapResim());
        }

    }

    public interface OnItemClickListener{
        //Bu interface, tıklanabilir öğelerin dinlenmesini sağlar.
        //OnItemClick adında bir metod içerir. Bu metod, tıklanan öğeyi temsil eden bir Kitap nesnesini parametre olarak alır.
        void OnItemClick(Kitap kitap);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


}
