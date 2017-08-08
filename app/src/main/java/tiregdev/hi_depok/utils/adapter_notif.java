package tiregdev.hi_depok.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

public class adapter_notif extends RecyclerView.Adapter<holder_notif> {
    private List<itemObjcect_notif> itemList;
    private Context context;

    public adapter_notif(Context context, List<itemObjcect_notif> itemList){
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
}
