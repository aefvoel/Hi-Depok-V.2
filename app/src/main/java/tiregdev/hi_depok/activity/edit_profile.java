package tiregdev.hi_depok.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tiregdev.hi_depok.R;

import static tiregdev.hi_depok.R.layout.activity_signup;

public class edit_profile extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar dateAndTime = Calendar.getInstance();
    Button tglLahir;
    DatePickerDialog.OnDateSetListener d =
            new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day){
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, month);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel();
                }
            };

    private void updateLabel(){
        tglLahir.setText(
                sdf.format(dateAndTime.getTime()));
    }

    private void settingTanggal(){
        new DatePickerDialog(edit_profile.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tglLahir = (Button) findViewById(R.id.tglLahir);
        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingTanggal();
            }
        });

        ImageView avatar = (ImageView) findViewById(R.id.edit);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(edit_profile.this, change_avatar.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                edit_profile.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
