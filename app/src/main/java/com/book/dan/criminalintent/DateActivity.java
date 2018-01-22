package com.book.dan.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

public class DateActivity extends SingleFragmentActivity {
    private final static String DATE_EXTRA = "Date";

    public static Intent newInstance(Context context, Date date){
        Intent intent = new Intent(context,DateActivity.class);
        intent.putExtra(DATE_EXTRA,date);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return DatePickerFragment.newInstance((Date)getIntent().getSerializableExtra(DATE_EXTRA));
    }
}
