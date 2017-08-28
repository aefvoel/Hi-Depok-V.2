package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.itemObject_modul;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class adapter_modul extends RecyclerView.Adapter<adapter_modul.holder_modul> {
    private List<itemObject_modul> itemList;
    private Context context;

    public adapter_modul(Context context, List<itemObject_modul> itemList){
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
        holder.list_page.setText(itemList.get(position).getPage());
        holder.list_viewer.setText(itemList.get(position).getViewer());
        holder.list_cover.setImageResource(itemList.get(position).getCover());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_modul extends RecyclerView.ViewHolder {
        public TextView list_judul, list_pengarang, list_page, list_viewer;
        public ImageView list_cover;

        public holder_modul(View itemView){
            super(itemView);

            list_judul = (TextView)itemView.findViewById(R.id.judul);
            list_pengarang = (TextView)itemView.findViewById(R.id.pengarang);
            list_page = (TextView)itemView.findViewById(R.id.page);
            list_viewer = (TextView)itemView.findViewById(R.id.viewer);
            list_cover = (ImageView)itemView.findViewById(R.id.cover);

        }
    }
}
