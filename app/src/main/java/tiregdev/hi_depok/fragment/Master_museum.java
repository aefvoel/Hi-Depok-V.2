package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tiregdev.hi_depok.R;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_museum extends Fragment{

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_museum, container, false);
        return v;
    }
}
