package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.detail_karya;
import tiregdev.hi_depok.activity.detail_modul;
import tiregdev.hi_depok.model.ModulPost;
import tiregdev.hi_depok.model.itemObject_modul;
import tiregdev.hi_depok.utils.AppConfig;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class adapter_modul extends RecyclerView.Adapter<adapter_modul.holder_modul> {
    private List<ModulPost> itemList;
    private Context context;

    public adapter_modul(Context context, List<ModulPost> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_modul.holder_modul onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_master_modul,null);
        adapter_modul.holder_modul hn = new holder_modul(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_modul.holder_modul holder, int position){
        holder.list_judul.setText(itemList.get(position).getJudul());
        holder.list_pengarang.setText(itemList.get(position).getPengarang());
        holder.list_page.setText(itemList.get(position).getJml_halaman());
        holder.list_viewer.setText(itemList.get(position).getId_modul());
        holder.list_kategori.setText(itemList.get(position).getKategori());

        Glide.with(context).load(AppConfig.IMG_LINK + "modul/" + itemList.get(position).getFoto()).into(holder.list_cover);
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_modul extends RecyclerView.ViewHolder {
        public TextView list_judul, list_pengarang, list_page, list_viewer, list_kategori;
        public ImageView list_cover;

        public holder_modul(final View itemView){
            super(itemView);

            list_judul = (TextView)itemView.findViewById(R.id.judul);
            list_pengarang = (TextView)itemView.findViewById(R.id.pengarang);
            list_page = (TextView)itemView.findViewById(R.id.page);
            list_viewer = (TextView)itemView.findViewById(R.id.viewer);
            list_kategori = (TextView)itemView.findViewById(R.id.kategori);
            list_cover = (ImageView)itemView.findViewById(R.id.cover);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, detail_modul.class);
                    intent.putExtra("JUDUL", itemList.get(getAdapterPosition()).getJudul());
                    intent.putExtra("PENGARANG", itemList.get(getAdapterPosition()).getPengarang());
                    intent.putExtra("PAGE", itemList.get(getAdapterPosition()).getJml_halaman());
                    intent.putExtra("VIEWER", itemList.get(getAdapterPosition()).getId_modul());
                    intent.putExtra("KATEGORI", itemList.get(getAdapterPosition()).getKategori());
                    intent.putExtra("COVER", itemList.get(getAdapterPosition()).getFoto());
                    intent.putExtra("LINK", itemList.get(getAdapterPosition()).getLink());
                    context.startActivity(intent);
                }
            });
        }
    }
}
