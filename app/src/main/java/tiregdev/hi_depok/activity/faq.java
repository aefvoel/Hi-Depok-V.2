package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import tiregdev.hi_depok.R;

import static tiregdev.hi_depok.R.layout.activity_signup;

public class faq extends AppCompatActivity {

    ExpandableRelativeLayout expandableLayout1,expandableLayout2,expandableLayout3,
            expandableLayout4,expandableLayout5,expandableLayout6,expandableLayout7,
            expandableLayout8,expandableLayout9, expandableLayout10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                faq.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void expandableButton1(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse
    }

    public void expandableButton2(View view) {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void expandableButton3(View view) {
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout3.toggle(); // toggle expand and collapse
    }

    public void expandableButton4(View view) {
        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
        expandableLayout4.toggle(); // toggle expand and collapse
    }

    public void expandableButton5(View view) {
        expandableLayout5 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout5);
        expandableLayout5.toggle(); // toggle expand and collapse
    }

    public void expandableButton6(View view) {
        expandableLayout6 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout6);
        expandableLayout6.toggle(); // toggle expand and collapse
    }

    public void expandableButton7(View view) {
        expandableLayout7 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout7);
        expandableLayout7.toggle(); // toggle expand and collapse
    }

    public void expandableButton9(View view) {
        expandableLayout9 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout9);
        expandableLayout9.toggle(); // toggle expand and collapse
    }

    public void expandableButton10(View view) {
        expandableLayout10 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout10);
        expandableLayout10.toggle(); // toggle expand and collapse
    }
}
