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

import de.hdodenhof.circleimageview.CircleImageView;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.DetailNewsActivity;
import tiregdev.hi_depok.model.RssItem;

/**
 * Created by TiregDev on 25/08/2017.
 */

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.holder_news> {


    private List<RssItem> itemList;
    private Context context;
    private String link;
    private String title;
    private String pubDate;
    private String portal;
    private int displaySize;

    public RSSAdapter(Context context, List<RssItem> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_news onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_blog,null);
        final holder_news hn = new holder_news(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(RSSAdapter.holder_news holder, int position){
        title = itemList.get(position).getTitle();
        link = itemList.get(position).getLink();
        pubDate = itemList.get(position).getPubDate().substring(0, 16);
        portal = itemList.get(position).getPortal();
        holder.list_title.setText(title);
        holder.list_portal.setText(portal);
        holder.list_time.setText(pubDate);
        if(link.contains("depoknews")){
            Glide.with(context).load("https://journalistdays2015.files.wordpress.com/2015/02/logo-depoknews-com.jpg").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("depokpos")){
            Glide.with(context).load("https://pbs.twimg.com/profile_images/691510248029294592/v_fq5Vt0_400x400.png").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("depokgoid")){
            Glide.with(context).load("https://www.depok.go.id/berkas-unggah/2017/08/pp_baru.jpg").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("hariandepok")){
            Glide.with(context).load("http://www.hariandepok.com/wp-content/uploads/2014/09/HARIAN-DEPOK-JOSS.png").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("radardepok")){
            Glide.with(context).load("http://cdn-kisikisi.qerja.com/assets/companies/images/logo/radar_depok_fb.jpg").fitCenter().into(holder.list_picTitle);
        }
    }


    @Override
    public int getItemCount(){

        if(displaySize > itemList.size())
            return itemList.size();
        else
            return displaySize;
    }

    public void setDisplayCount(int numberOfEntries) {
        displaySize = numberOfEntries;
        notifyDataSetChanged();

    }

    public class holder_news extends RecyclerView.ViewHolder {
        public TextView list_title;
        public TextView list_portal;
        public TextView list_time;
        public CircleImageView list_picTitle;
        public holder_news(final View itemView){
            super(itemView);

            list_title = (TextView)itemView.findViewById(R.id.titleNews);
            list_portal = (TextView)itemView.findViewById(R.id.source);
            list_time = (TextView)itemView.findViewById(R.id.time);
            list_picTitle = (CircleImageView)itemView.findViewById(R.id.imageTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context v = view.getContext();
                    Intent intent = new Intent(v, DetailNewsActivity.class);
                    intent.putExtra("url", itemList.get(getAdapterPosition()).getLink());
                    v.startActivity(intent);
                }
            });

        }
    }
}
