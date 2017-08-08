package tiregdev.hi_depok.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tiregdev.hi_depok.R;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

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
