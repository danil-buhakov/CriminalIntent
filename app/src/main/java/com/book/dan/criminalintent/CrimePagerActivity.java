package com.book.dan.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "id";
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button mButtonFirst;
    private Button mButtonLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mButtonFirst = (Button) findViewById(R.id.btn_first);
        mButtonLast = (Button) findViewById(R.id.btn_last);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                int curr = mViewPager.getCurrentItem();
                if(curr==0) {
                    mButtonFirst.setEnabled(false);
                    mButtonLast.setEnabled(true);
                }
                else
                    if(curr==(mCrimes.size()-1)) {
                        mButtonFirst.setEnabled(true);
                        mButtonLast.setEnabled(false);
                    }
                    else{
                        mButtonFirst.setEnabled(true);
                        mButtonLast.setEnabled(true);
                    }
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        for(int i=0; i<mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                if(i==0)
                    mButtonFirst.setEnabled(false);
                if(i==(mCrimes.size()-1))
                    mButtonLast.setEnabled(false);
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size());
            }
        });
    }

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,id);
        return intent;
    }
}
