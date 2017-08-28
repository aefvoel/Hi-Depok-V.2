package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.itemObject_karya;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class adapter_karya extends RecyclerView.Adapter<adapter_karya.holder_karya> {
    private List<itemObject_karya> itemList;
    private Context context;

    public adapter_karya(Context context, List<itemObject_karya> itemList){
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
        holder.list_username.setText(itemList.get(position).getUsername());
        holder.list_location.setText(itemList.get(position).getLocation());
        holder.list_likeTxt.setText(itemList.get(position).getLikeTxt());
        holder.list_commentTxt.setText(itemList.get(position).getCommentTxt());
        holder.list_postTxt.setText(itemList.get(position).getPostTxt());
        holder.list_shareTxt.setText(itemList.get(position).getShareTxt());
        holder.list_Avatar.setImageResource(itemList.get(position).getAvatar());
        holder.list_imagePost.setImageResource(itemList.get(position).getImagePost());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_karya extends RecyclerView.ViewHolder {
        public TextView list_username, list_location, list_likeTxt, list_commentTxt, list_postTxt, list_shareTxt;
        public ImageView list_Avatar, list_imagePost;

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
        }
    }
}
