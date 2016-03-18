package com.projeto.fragment;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by leo on 3/18/16.
 */
public class DataDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private DatePicker datePicker;
    private String data;



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    public static DataDialogFragment newInstance(String hora) {
        return null;
    }

    public void abrir(FragmentManager fragmentManager) {

    }
}
