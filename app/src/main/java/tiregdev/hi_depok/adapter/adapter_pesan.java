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
import tiregdev.hi_depok.model.itemObject_museum;
import tiregdev.hi_depok.model.itemObject_pesan;

/**
 * Created by TiregDev on 29/08/2017.
 */

public class adapter_pesan extends RecyclerView.Adapter<adapter_pesan.holder_pesan> {
    private List<itemObject_pesan> itemList;
    private Context context;

    public adapter_pesan(Context context, List<itemObject_pesan> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public adapter_pesan.holder_pesan onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesan,null);
        adapter_pesan.holder_pesan hn = new holder_pesan(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_pesan.holder_pesan holder, int position){
        holder.list_user.setText(itemList.get(position).getUser());
        holder.list_time.setText(itemList.get(position).getTime());
        holder.list_chat.setText(itemList.get(position).getChat());
        holder.list_avatar.setImageResource(itemList.get(position).getAvatar());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_pesan extends RecyclerView.ViewHolder {
        public TextView list_user, list_time, list_chat;
        public ImageView list_avatar;

        public holder_pesan(View itemView){
            super(itemView);

            list_user = (TextView)itemView.findViewById(R.id.user);
            list_time = (TextView)itemView.findViewById(R.id.time);
            list_chat= (TextView)itemView.findViewById(R.id.chat);
            list_avatar = (ImageView)itemView.findViewById(R.id.avatar);

        }
    }
}
