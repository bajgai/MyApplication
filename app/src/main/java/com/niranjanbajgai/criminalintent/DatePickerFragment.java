package com.niranjanbajgai.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by niranjanbajgai on 14-11-04.
 */
public class DatePickerFragment extends android.support.v4.app.DialogFragment {
    public static final String EXTRA_DATA = "com.niranjanbajgai.criminalintent.date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATA, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATA);

        //create calendar to get year month and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year,month,day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                mDate = new GregorianCalendar(year,month,day).getTime();
                getArguments().putSerializable(EXTRA_DATA, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
