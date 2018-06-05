package tiregdev.hi_depok.fragment;

/**
 * Created by SONY-VAIO on 12/28/2017.
 */
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import tiregdev.hi_depok.R;

/**
 * SearchTimelineFragment demonstrates a TimelineListAdapter with a SearchTimeline.
 */
public class TwitterTimeline extends ListFragment {

    public static TwitterTimeline newInstance() {
        return new TwitterTimeline();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("depokita")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(),
                userTimeline);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
}
