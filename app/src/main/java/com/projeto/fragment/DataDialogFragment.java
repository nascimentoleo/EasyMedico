package com.projeto.fragment;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;

import java.util.Calendar;


/**
 * Created by leo on 3/18/16.
 */
public class DataDialogFragment extends DatePickerDialog {

    private static Calendar calendario = Calendar.getInstance();
    private static final String DATA_DIALOG_TAG = "dataDialog";

    public DataDialogFragment(Context context, OnDateSetListener callBack) {
        super(context, callBack, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));

    }

}
