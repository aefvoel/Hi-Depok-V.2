package tiregdev.hi_depok.fragment;

/**
 * Created by SONY-VAIO on 12/28/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import tiregdev.hi_depok.R;

public class TwitterTimeline extends Fragment {

    View view;
    UserTimeline userTimeline;
    TweetTimelineListAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        setUpTimeline();
        return view;
    }

    private void setUpTimeline() {
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query("#depok").build();

        final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(getActivity(), searchTimeline);

        ListView timelineView = (ListView) getActivity().findViewById(R.id.event_timeline);
        timelineView.setEmptyView(getActivity().findViewById(R.id.empty_timeline));
        timelineView.setAdapter(timelineAdapter);
    }
}
