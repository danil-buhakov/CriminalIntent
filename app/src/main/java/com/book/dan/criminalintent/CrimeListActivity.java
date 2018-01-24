package com.book.dan.criminalintent;


import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_twopane;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
