package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.detail_karya;
import tiregdev.hi_depok.activity.detail_kos;
import tiregdev.hi_depok.model.itemObject_komen_karya;
import tiregdev.hi_depok.model.itemObject_kos;

/**
 * Created by TiregDev on 04/10/2017.
 */

public class adapter_kos extends RecyclerView.Adapter<adapter_kos.holder_kos> {
    private List<itemObject_kos> itemList;
    private Context context;

    public adapter_kos(Context context, List<itemObject_kos> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_kos.holder_kos onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kos,null);
        adapter_kos.holder_kos hn = new adapter_kos.holder_kos(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_kos.holder_kos holder, int position){
        holder.nama.setText(itemList.get(position).getNama());
        holder.alamat.setText(itemList.get(position).getAlamat());
        holder.harga.setText(itemList.get(position).getHarga());
        holder.image.setImageResource(itemList.get(position).getImage());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_kos extends RecyclerView.ViewHolder {
        public TextView nama, alamat, harga;
        public ImageView image;

        public holder_kos(View itemView){
            super(itemView);

            nama = (TextView)itemView.findViewById(R.id.nama_kos);
            alamat = (TextView)itemView.findViewById(R.id.alamat_kos);
            harga = (TextView)itemView.findViewById(R.id.harga_kos);
            image = (ImageView)itemView.findViewById(R.id.img_kos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, detail_kos.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
