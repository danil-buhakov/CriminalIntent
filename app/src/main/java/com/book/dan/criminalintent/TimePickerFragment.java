package com.book.dan.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment{
    private static final String ARG_DATE = "date";
    private static final String TIME_DATA = "time";
    private TimePicker mTimePicker;
    private Date mDate;

    public static TimePickerFragment newInstance(Date date){
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker,null);
        mDate = (Date) getArguments().getSerializable(ARG_DATE);
        mTimePicker = v.findViewById(R.id.crime_time_picker);
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(Integer.parseInt(hour.format(mDate)));
            mTimePicker.setMinute(Integer.parseInt(minute.format(mDate)));
        }
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(mDate);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            calendar.set(Calendar.HOUR_OF_DAY,mTimePicker.getHour());
                            calendar.set(Calendar.MINUTE,mTimePicker.getMinute());
                        }
                        sendData(calendar.getTime());
                    }
                })
                .create();
    }
    private void sendData(Date date){
        if(getTargetFragment()==null)
            return;
        Intent data = new Intent();
        data.putExtra(TIME_DATA, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,data);
    }
    public static Date getDateFromIntent(Intent i){
        return (Date) i.getSerializableExtra(TIME_DATA);
    }
}
