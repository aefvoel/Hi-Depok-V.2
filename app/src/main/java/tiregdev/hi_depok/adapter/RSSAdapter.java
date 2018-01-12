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
            Glide.with(context).load("http://www.depokpos.com/go/wp-content/uploads/2017/12/logo-majalah.png").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("depokgoid")){
            Glide.with(context).load("https://www.depok.go.id/berkas-unggah/2017/08/pp_baru.jpg").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("hariandepok")){
            Glide.with(context).load("http://www.hariandepok.com/wp-content/uploads/2014/09/HARIAN-DEPOK-JOSS.png").fitCenter().into(holder.list_picTitle);
        }else if(link.contains("radardepok")){
            Glide.with(context).load("http://radardepok.com/wp-content/uploads/2016/12/Logo-Radar-Depok-Ok.jpg").fitCenter().into(holder.list_picTitle);
        }
        holder.shareNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Lihat konten ini pada Aplikasi Hi-Depok";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share Via"));
            }
        });
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
        public ImageView list_picTitle;
        public ImageView shareNews;

        public holder_news(final View itemView){
            super(itemView);

            list_title = (TextView)itemView.findViewById(R.id.titleNews);
            list_portal = (TextView)itemView.findViewById(R.id.source);
            list_time = (TextView)itemView.findViewById(R.id.time);
            list_picTitle = (ImageView)itemView.findViewById(R.id.imageTitle);
            shareNews = (ImageView)itemView.findViewById(R.id.forward);

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
