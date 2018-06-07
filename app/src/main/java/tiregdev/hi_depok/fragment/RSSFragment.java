package tiregdev.hi_depok.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.DetailNewsActivity;
import tiregdev.hi_depok.adapter.RSSAdapter;
import tiregdev.hi_depok.model.RssItem;
import tiregdev.hi_depok.utils.RssService;


public class RSSFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rView;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    TextView titleNews, sourceNews, dateNews;
    ImageView hotNews;
    LinearLayoutManager lLayout;
    DividerItemDecoration dividerItemDecoration;
    Button btnNews;
    RSSAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_blog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lLayout = new LinearLayoutManager(getActivity());
        titleNews = (TextView)view.findViewById(R.id.titleHotNews);
        sourceNews = (TextView)view.findViewById(R.id.source);
        dateNews = (TextView)view.findViewById(R.id.time);
        hotNews = (ImageView)view.findViewById(R.id.hotNews);
        rView = (RecyclerView)view.findViewById(R.id.view_news);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recycler_list);
        swipeRefreshRecyclerList.setOnRefreshListener(this);
        rView.setLayoutManager(lLayout);
        dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);
        rView.setNestedScrollingEnabled(false);
        btnNews = view.findViewById(R.id.btnLoadNews);
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setDisplayCount(adapter.getItemCount() + 5);
            }
        });

    }

    @Override
    public void onRefresh() {
        startService();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        swipeRefreshRecyclerList.setRefreshing(true);
        startService();


    }

    private void startService() {

        Intent intent = new Intent(getActivity(), RssService.class);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this BroadcastReceiver
     */
    private BroadcastReceiver resultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            swipeRefreshRecyclerList.setRefreshing(true);
            final List<RssItem> items = (List<RssItem>) intent.getSerializableExtra(RssService.ITEMS);

            if (items != null) {
                try{
                    titleNews.setText(items.get(0).getTitle());
                    sourceNews.setText(items.get(0).getPortal());
                    dateNews.setText(items.get(0).getPubDate().substring(0, 16));
                    hotNews.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), DetailNewsActivity.class);
                            intent.putExtra("url", items.get(0).getLink());
                            startActivity(intent);
                        }
                    });
                    Collections.sort(items, new Comparator<RssItem>() {
                        @Override
                        public int compare(RssItem data1, RssItem data2) {
                            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                            try {
                                Date date1 = formatter.parse(data1.getPubDate());
                                Date date2 = formatter.parse(data2.getPubDate());
                                return date2.compareTo(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                    adapter = new RSSAdapter(getActivity(), items);
                    adapter.setDisplayCount(10);
                    rView.setAdapter(adapter);

                    swipeRefreshRecyclerList.setRefreshing(false);
                }catch (IndexOutOfBoundsException e){
                    Log.w(e.getMessage(), e);
                }

            } else {
                Toast.makeText(getActivity(), "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();

                swipeRefreshRecyclerList.setRefreshing(false);
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(RssService.ACTION_RSS_PARSED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(resultReceiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(resultReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putParcelable("myState", lLayout.onSaveInstanceState());
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        lLayout.onRestoreInstanceState(savedInstanceState.getParcelable("myState"));
    }
}
