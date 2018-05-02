package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.MasterpieceKomentar;

/**
 * Created by Muhammad63 on 9/30/2017.
 */

public class DetailKaryaAdapter extends RecyclerView.Adapter<DetailKaryaAdapter.holder_komen_karya> {
    private List<MasterpieceKomentar> itemList;
    private Context context;

    public DetailKaryaAdapter(Context context, List<MasterpieceKomentar> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public DetailKaryaAdapter.holder_komen_karya onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komen_karya,null);
        DetailKaryaAdapter.holder_komen_karya hn = new DetailKaryaAdapter.holder_komen_karya(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(DetailKaryaAdapter.holder_komen_karya holder, int position){
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
