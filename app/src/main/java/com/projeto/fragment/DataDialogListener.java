package com.projeto.fragment;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by leo on 3/18/16.
 */
public class DataDialogListener implements DatePickerDialog.OnDateSetListener {

    private EditText editData;

    public DataDialogListener(EditText editData) {
        this.editData = editData;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String data = String.valueOf(dayOfMonth) + " /"
                + String.valueOf(monthOfYear+1) + " /" + String.valueOf(year);
       editData.setText(data);
    }
}
