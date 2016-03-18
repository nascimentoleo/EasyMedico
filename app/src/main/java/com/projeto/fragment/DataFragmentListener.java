package com.projeto.fragment;

import android.app.FragmentManager;
import android.view.View;

/**
 * Created by leo on 3/18/16.
 */
public class DataFragmentListener implements View.OnClickListener {

    private FragmentManager fragmentManager;

    public DataFragmentListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        DataDialogFragment dataDialogFragment = DataDialogFragment.newInstance(null);
        dataDialogFragment.abrir(this.fragmentManager);

    }
}
