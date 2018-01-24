package com.book.dan.criminalintent;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.book.dan.criminalintent.database.CrimeCursorWrapper;
import com.book.dan.criminalintent.database.CrimeDbHelper;
import com.book.dan.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
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
    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursorWrapper = queryCrimes(null,null);
        try{
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){
        CrimeCursorWrapper cursorWrapper = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
        try{
            if(cursorWrapper.getCount()==0)
                return null;
            cursorWrapper.moveToFirst();
            return cursorWrapper.getCrime();
        }
        finally {
            cursorWrapper.close();
        }
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID+" = ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String where, String[] whereArgs){
        Cursor curs = mDatabase.query(
                CrimeTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(curs);
    }

    public File getPhotoFile(Crime crime){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir,crime.getPhotoFilename());
    }

    public void deleteCrime(Crime crime){
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID+" = ?", new String[]{crime.getId().toString()});
    }
}
