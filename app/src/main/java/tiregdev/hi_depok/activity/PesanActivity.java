package tiregdev.hi_depok.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.Dialog;
import tiregdev.hi_depok.model.Messages;
import tiregdev.hi_depok.model.UserMessage;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;

public class PesanActivity extends DemoDialogsActivity {



    private Dialog dialogObjek;
    private Messages messageObjek;
    private UserMessage userObjek;

    private JSONObject jsonObject;

    public static void open(Context context) {
        context.startActivity(new Intent(context, PesanActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                PesanActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        PesanListActivity.open(this);
    }

    private void initAdapter() {

        dialogsAdapter = new DialogsListAdapter<>(super.imageLoader);
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        SQLiteHandler db;
        db = new SQLiteHandler(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_CHAT + "?id=" + db.getUserDetails().get("uid"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<UserMessage> users = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        users.add(new UserMessage(
                                jsonObject.getString("id_pengirim"),
                                jsonObject.getString("username"),
                                jsonObject.getString("foto"),
                                true));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayList<Messages> messages = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        String dateString = jsonObject.getString("waktu");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        Date convertedDate = new Date();

                        try {
                            convertedDate = dateFormat.parse(dateString);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        messages.add(new Messages(
                                jsonObject.getString("id_pesan"),
                                userObjek,
                                jsonObject.getString("isi_pesan"),
                                convertedDate));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayList<Dialog> dialogs = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        dialogs.add(new Dialog(
                                jsonObject.getString("id_pesan"),
                                jsonObject.getString("username"),
                                jsonObject.getString("foto"),
                                users,
                                messageObjek,
                                1));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dialogsAdapter.setItems(dialogs);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        dialogsList.setAdapter(dialogsAdapter);
    }

    //for example
    private void onNewMessage(String dialogId, Messages message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    //for example
    private void onNewDialog(Dialog dialog) {
        dialogsAdapter.addItem(dialog);
    }



}
