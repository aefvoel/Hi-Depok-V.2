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
import tiregdev.hi_depok.model.MasterpieceKomentar;
import tiregdev.hi_depok.model.itemObject_karya;
import tiregdev.hi_depok.model.itemObject_komen_karya;

/**
 * Created by Muhammad63 on 9/30/2017.
 */

public class adapter_komen_karya extends RecyclerView.Adapter<adapter_komen_karya.holder_komen_karya> {
    private List<MasterpieceKomentar> itemList;
    private Context context;

    public adapter_komen_karya(Context context, List<MasterpieceKomentar> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_komen_karya.holder_komen_karya onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komen_karya,null);
        adapter_komen_karya.holder_komen_karya hn = new adapter_komen_karya.holder_komen_karya(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_komen_karya.holder_komen_karya holder, int position){
        holder.uname.setText(itemList.get(position).getNamaUser());
        holder.time.setText(itemList.get(position).getWaktu());
        holder.komen.setText(itemList.get(position).getIsiKomentar());

        Glide.with(context).load(itemList.get(position).getAvatar()).placeholder(R.drawable.no_image).into(holder.avatar);
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_komen_karya extends RecyclerView.ViewHolder {
        public TextView uname, time, komen;
        public ImageView avatar;

        public holder_komen_karya(View itemView){
            super(itemView);

            uname = (TextView)itemView.findViewById(R.id.uname);
            time = (TextView)itemView.findViewById(R.id.time);
            komen = (TextView)itemView.findViewById(R.id.komen);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
        }
    }
}
