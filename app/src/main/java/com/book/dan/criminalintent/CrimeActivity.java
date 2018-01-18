package com.book.dan.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "id";
    private static final String CRIME_ID="id";

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,id);
        return intent;
    }

    public static UUID getIdFromIntent(Intent data){
        return (UUID)data.getSerializableExtra(CRIME_ID);
    }

    @Override
    protected Fragment createFragment() {
        UUID id =(UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        Intent data = new Intent();
        data.putExtra(CRIME_ID,id);
        setResult(Activity.RESULT_OK,data);
        return CrimeFragment.newInstance(id);
    }
}
