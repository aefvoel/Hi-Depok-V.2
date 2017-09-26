package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import tiregdev.hi_depok.R;

public class submit_master_karya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_master_karya);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View exitDialogView = factory.inflate(R.layout.alert_submit, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(exitDialogView);
        exitDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_master_karya.this.finish();
            }
        });
        exitDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
    }
}
