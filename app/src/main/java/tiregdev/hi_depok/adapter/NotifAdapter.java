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
import tiregdev.hi_depok.model.Notifikasi;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.holder_notif> {
    private List<Notifikasi> itemList;
    private Context context;

    public NotifAdapter(Context context, List<Notifikasi> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_notif onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notif,null);
        holder_notif hn = new holder_notif(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_notif holder, int position){
        holder.list_user.setText(itemList.get(position).getUser());
        holder.list_notifTxt.setText(itemList.get(position).getNotifTxt());
        holder.list_time.setText(itemList.get(position).getTime());
        holder.list_picNotif.setImageResource(itemList.get(position).getPicNotif());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_notif extends RecyclerView.ViewHolder {
        public TextView list_user;
        public TextView list_notifTxt;
        public TextView list_time;
        public ImageView list_picNotif;

        public holder_notif(View itemView){
            super(itemView);

            list_user = (TextView)itemView.findViewById(R.id.user);
            list_notifTxt = (TextView)itemView.findViewById(R.id.notifTxt);
            list_time = (TextView)itemView.findViewById(R.id.time);
            list_picNotif = (ImageView)itemView.findViewById(R.id.picNotif);
        }
    }
}
