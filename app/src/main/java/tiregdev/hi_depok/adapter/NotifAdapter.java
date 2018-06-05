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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.ChatRoomActivity;
import tiregdev.hi_depok.activity.DetailModulActivity;
import tiregdev.hi_depok.model.Notifikasi;
import tiregdev.hi_depok.utils.AppConfig;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.holder_notif> {
    private List<Notifikasi> itemList;
    private Context context;
    public static String today;

    public NotifAdapter(Context context, List<Notifikasi> itemList) {
        this.itemList = itemList;
        this.context = context;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public holder_notif onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notif, null);
        holder_notif hn = new holder_notif(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_notif holder, int position) {

        String timestamp = getTimeStamp(itemList.get(position).getTime());

        holder.list_user.setText(itemList.get(position).getUser());
        holder.list_notifTxt.setText(itemList.get(position).getText());
        holder.list_time.setText(timestamp);
        Glide.with(context).load(itemList.get(position).getFoto()).into(holder.list_picNotif);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

    public class holder_notif extends RecyclerView.ViewHolder {
        public TextView list_user;
        public TextView list_notifTxt;
        public TextView list_time;
        public ImageView list_picNotif;

        public holder_notif(View itemView) {
            super(itemView);

            list_user = (TextView) itemView.findViewById(R.id.user);
            list_notifTxt = (TextView) itemView.findViewById(R.id.notifTxt);
            list_time = (TextView) itemView.findViewById(R.id.time);
            list_picNotif = (ImageView) itemView.findViewById(R.id.picNotif);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    if (itemList.get(getAdapterPosition()).getType().equals("thread")) {
                        Intent intent = new Intent(context, ChatRoomActivity.class);
                        intent.putExtra("chat_room_id", itemList.get(getAdapterPosition()).getGoToId());
                        intent.putExtra("name",  itemList.get(getAdapterPosition()).getName());
                        context.startActivity(intent);
                    }

                }
            });
        }

    }
}
