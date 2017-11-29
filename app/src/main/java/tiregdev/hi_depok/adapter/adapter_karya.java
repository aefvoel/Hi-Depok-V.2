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

import tiregdev.hi_depok.model.MasterpiecePost;
import tiregdev.hi_depok.utils.AppConfig;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class adapter_karya extends RecyclerView.Adapter<adapter_karya.holder_karya> {
    private List<MasterpiecePost> itemList;
    private Context context;

    public adapter_karya(Context context, List<MasterpiecePost> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_karya.holder_karya onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_master_karya,null);
        adapter_karya.holder_karya hn = new holder_karya(layoutView);
        return hn;
    }


    @Override
    public void onBindViewHolder(adapter_karya.holder_karya holder, int position){
        holder.list_username.setText(itemList.get(position).getNama_peraih());
        holder.list_location.setText(itemList.get(position).getTingkat());
        holder.list_likeTxt.setText(itemList.get(position).getJumlah_suka());
        holder.list_commentTxt.setText(itemList.get(position).getJumlah_komentar());
        holder.list_postTxt.setText(itemList.get(position).getDeskripsi());
        holder.list_shareTxt.setText(itemList.get(position).getJumlah_suka());
        holder.list_time.setText(itemList.get(position).getTgl_post());

        Glide.with(context).load(AppConfig.IMG_MASTERPIECE + itemList.get(position).getImage()).placeholder(R.drawable.no_image).into(holder.list_imagePost);
//        Glide.with(context).load(itemList.get(position).getImage()).placeholder(R.drawable.no_image).into(holder.list_Avatar);
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_karya extends RecyclerView.ViewHolder {
        public TextView list_username, list_location, list_likeTxt, list_commentTxt, list_postTxt, list_shareTxt, list_time;
        public ImageView list_Avatar, list_imagePost, list_status;

        public holder_karya(View itemView){
            super(itemView);

            list_username = (TextView)itemView.findViewById(R.id.username);
            list_location = (TextView)itemView.findViewById(R.id.location);
            list_likeTxt = (TextView)itemView.findViewById(R.id.likeText);
            list_commentTxt = (TextView)itemView.findViewById(R.id.commentText);
            list_postTxt = (TextView)itemView.findViewById(R.id.postTxt);
            list_shareTxt = (TextView)itemView.findViewById(R.id.shareText);
            list_Avatar = (ImageView)itemView.findViewById(R.id.avatar);
            list_imagePost = (ImageView)itemView.findViewById(R.id.imagePost);
            list_status = (ImageView)itemView.findViewById(R.id.status);
            list_time = (TextView)itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, detail_karya.class);
                    intent.putExtra("NAMA", itemList.get(getAdapterPosition()).getNama_prestasi());
                    intent.putExtra("DESKRIPSI", itemList.get(getAdapterPosition()).getDeskripsi());
                    intent.putExtra("PERAIH", itemList.get(getAdapterPosition()).getNama_peraih());
                    intent.putExtra("TANGGAL", itemList.get(getAdapterPosition()).getTgl_post());
                    intent.putExtra("KATEGORI", itemList.get(getAdapterPosition()).getKategori());
                    intent.putExtra("TINGKAT", itemList.get(getAdapterPosition()).getTingkat());
                    intent.putExtra("IMAGE", itemList.get(getAdapterPosition()).getImage());
                    context.startActivity(intent);
                }
            });
        }
    }
}
