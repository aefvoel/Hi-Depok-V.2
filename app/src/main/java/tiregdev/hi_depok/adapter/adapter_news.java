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
import tiregdev.hi_depok.model.itemObject_news;

/**
 * Created by TiregDev on 25/08/2017.
 */

public class adapter_news extends RecyclerView.Adapter<adapter_news.holder_news> {
    private List<itemObject_news> itemList;
    private Context context;

    public adapter_news(Context context, List<itemObject_news> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_news.holder_news onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,null);
        adapter_news.holder_news hn = new adapter_news.holder_news(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_news.holder_news holder, int position){
        holder.list_title.setText(itemList.get(position).getTitle());
        holder.list_portal.setText(itemList.get(position).getPortal());
        holder.list_time.setText(itemList.get(position).getTime());
        holder.list_picTitle.setImageResource(itemList.get(position).getPicTitle());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_news extends RecyclerView.ViewHolder {
        public TextView list_title;
        public TextView list_portal;
        public TextView list_time;
        public ImageView list_picTitle;

        public holder_news(View itemView){
            super(itemView);

            list_title = (TextView)itemView.findViewById(R.id.titleNews);
            list_portal = (TextView)itemView.findViewById(R.id.source);
            list_time = (TextView)itemView.findViewById(R.id.time);
            list_picTitle = (ImageView)itemView.findViewById(R.id.imageTitle);
        }
    }
}
