package tiregdev.hi_depok.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * Created by SONY-VAIO on 10/25/2017.
 */

public class BaseFragment extends Fragment {
    Bundle savedState;

    public BaseFragment() {
        super();
        if (getArguments() == null)
            setArguments(new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!restoreStateFromArguments()) {
            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }


    public void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle("internalSavedViewState8954201239547", savedState);
        }
    }


    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("internalSavedViewState8954201239547");
        if (savedState != null) {
            onRestoreState(savedState);
            return true;
        }
        return false;
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    protected void onSaveState(Bundle outState) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments();
    }
}