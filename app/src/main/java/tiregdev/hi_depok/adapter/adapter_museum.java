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
import tiregdev.hi_depok.activity.detail_modul;
import tiregdev.hi_depok.activity.detail_museum;
import tiregdev.hi_depok.model.itemObject_museum;

/**
 * Created by TiregDev on 29/08/2017.
 */

public class adapter_museum extends RecyclerView.Adapter<adapter_museum.holder_museum> {
    private List<itemObject_museum> itemList;
    private Context context;

    public adapter_museum(Context context, List<itemObject_museum> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public adapter_museum.holder_museum onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_master_museum,null);
        adapter_museum.holder_museum hn = new holder_museum(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_museum.holder_museum holder, int position){
        holder.list_judul.setText(itemList.get(position).getJudul());
        holder.list_nama.setText(itemList.get(position).getNama());
        holder.list_tahun.setText(itemList.get(position).getTahun());
        holder.list_picMuseum.setImageResource(itemList.get(position).getPicMuseum());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_museum extends RecyclerView.ViewHolder {
        public TextView list_judul, list_nama, list_tahun;
        public ImageView list_picMuseum;

        public holder_museum(View itemView){
            super(itemView);

            list_judul = (TextView)itemView.findViewById(R.id.titleMuseum);
            list_nama = (TextView)itemView.findViewById(R.id.nama);
            list_tahun= (TextView)itemView.findViewById(R.id.tahun);
            list_picMuseum = (ImageView)itemView.findViewById(R.id.picMuseum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, detail_museum.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
