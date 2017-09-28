package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.detail_search;
import tiregdev.hi_depok.model.itemObject_searchData;

/**
 * Created by Muhammad63 on 9/1/2017.
 */

public class adapter_searchData extends RecyclerView.Adapter<adapter_searchData.holder_searchData> {
    private List<itemObject_searchData> itemList;
    private Context context;

    public adapter_searchData(Context context, List<itemObject_searchData> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(ArrayList<itemObject_searchData> modelList) {
        this.itemList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public holder_searchData onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_data,null);
        holder_searchData hn = new holder_searchData(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_searchData.holder_searchData holder, int position){
        holder.list_namaTempat.setText(itemList.get(position).getNamaTempat());
        holder.list_alamat.setText(itemList.get(position).getAlamat());
        holder.list_jarak.setText(itemList.get(position).getJarak());
        holder.list_icon.setImageResource(itemList.get(position).getIcon());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_searchData extends RecyclerView.ViewHolder {
        public TextView list_namaTempat, list_alamat, list_jarak;
        public ImageView list_icon;

        public holder_searchData(View itemView){
            super(itemView);

            list_namaTempat = (TextView)itemView.findViewById(R.id.namaTempat);
            list_alamat = (TextView)itemView.findViewById(R.id.alamat);
            list_jarak = (TextView)itemView.findViewById(R.id.jarak);
            list_icon = (ImageView)itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, detail_search.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}

