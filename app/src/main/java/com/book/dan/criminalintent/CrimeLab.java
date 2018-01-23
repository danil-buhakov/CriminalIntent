package com.book.dan.criminalintent;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.book.dan.criminalintent.database.CrimeDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context;
        mDatabase = new CrimeDbHelper(mContext).getWritableDatabase();
        mCrimes = new ArrayList<>();
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime c:mCrimes){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }
}
