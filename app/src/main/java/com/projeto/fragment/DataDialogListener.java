package com.projeto.fragment;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import com.projeto.lib.DataLib;

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
        String data = DataLib.getDataFormatada(dayOfMonth, monthOfYear+1, year);
        editData.setText(data);
    }
}
