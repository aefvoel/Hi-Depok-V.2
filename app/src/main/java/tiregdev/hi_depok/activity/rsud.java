package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tiregdev.hi_depok.R;

import static tiregdev.hi_depok.R.layout.activity_signup;

public class rsud extends AppCompatActivity {

    TextView regis1, regis2, regis3, ketentuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsud);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRegis1();
        setRegis2();
        setRegis3();
        setKetentuan();
    }

    public void setRegis1(){
        regis1 = (TextView) findViewById(R.id.regis_baru);
        regis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(rsud.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_regis_1, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(rsud.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.kirim).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.hide();
                    }
                });
                exitDialog.show();
            }
        });
    }

    public void setRegis2(){
        regis2 = (TextView) findViewById(R.id.regis_lama);
        regis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(rsud.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_regis_2, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(rsud.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.kirim).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.hide();
                    }
                });
                exitDialog.show();
            }
        });
    }

    public void setRegis3(){
        regis3 = (TextView) findViewById(R.id.regis_berobat);
        regis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(rsud.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_regis_3, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(rsud.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.kirim).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.hide();
                    }
                });
                exitDialog.show();
            }
        });
    }

    public void setKetentuan(){
        ketentuan = (TextView) findViewById(R.id.ketentuan);
        ketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(rsud.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_ketentuan, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(rsud.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                rsud.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
