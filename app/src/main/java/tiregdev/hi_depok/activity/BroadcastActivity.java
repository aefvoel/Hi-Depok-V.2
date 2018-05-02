package tiregdev.hi_depok.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.ChatRoomThreadAdapter;
import tiregdev.hi_depok.model.Message;
import tiregdev.hi_depok.model.User;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.GCMConfig;
import tiregdev.hi_depok.utils.NotificationUtils;
import tiregdev.hi_depok.utils.SQLiteHandler;

public class BroadcastActivity extends AppCompatActivity {

    private String TAG = BroadcastActivity.class.getSimpleName();

    private String chatRoomId;
    private RecyclerView recyclerView;
    private ChatRoomThreadAdapter mAdapter;
    private ArrayList<Message> messageArrayList;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private EditText inputMessage;
    private Button btnSend;
    private Map<String, ArrayList<String>> statistik;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = new SQLiteHandler(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        messageArrayList = new ArrayList<>();
        fetchChatThread();
        // self user id is to identify the message owner
        String selfUserId = AppController.getInstance().getPrefManager().getUser().getId();

        mAdapter = new ChatRoomThreadAdapter(this, messageArrayList, selfUserId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMConfig.PUSH_NOTIFICATION)) {
                    // new push message is received
                    handlePushNotification(intent);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMConfig.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Handling new push message, will add the message to
     * recycler view and scroll it to bottom
     * */
    private void handlePushNotification(Intent intent) {
        Message message = (Message) intent.getSerializableExtra("message");
        String chatRoomId = intent.getStringExtra("chat_room_id");

        if (message != null && chatRoomId != null) {
            messageArrayList.add(message);
            mAdapter.notifyDataSetChanged();
            if (mAdapter.getItemCount() > 1) {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount() - 1);
            }
        }
    }

    /**
     * Fetching all the messages of a single chat room
     * */
    private void fetchChatThread() {

        for (int i = 0; i < db.getBroadcastMessages().get("id").size(); i++) {

            String userId = "12345678";
            String userName = "Hi-Depok Admin";
            User user = new User(userId, userName, null);

            Message message = new Message();
            message.setId(db.getBroadcastMessages().get("id").get(i));
            message.setMessage(db.getBroadcastMessages().get("message").get(i));
            message.setCreatedAt(db.getBroadcastMessages().get("created_at").get(i));
            message.setUser(user);

            messageArrayList.add(message);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                BroadcastActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

