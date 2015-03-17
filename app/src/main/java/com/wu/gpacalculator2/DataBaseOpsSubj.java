package com.wu.gpacalculator2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Swaggiott on 10/3/2015.
 */
public class DataBaseOpsSubj extends SQLiteOpenHelper {

    public static final int database_version = 1;


    public DataBaseOpsSubj(Context context) {
        super(context, GradesData.DataInfo.TABLE_NAME2, null, database_version);
        Log.d("DATA", "SUBJDONE");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + GradesData.DataInfo.TABLE_NAME2 + " ( " +
                GradesData.DataInfo.SUBJ + " TEXT " +
                ");";

        db.execSQL(CREATE_QUERY);
        Log.d("DATA", "DATADONE, TABLE CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GradesData.DataInfo.TABLE_NAME2);

        this.onCreate(db);
    }

    public void subjects(String subj) {
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(GradesData.DataInfo.SUBJ, subj);

        SQ.insert(GradesData.DataInfo.TABLE_NAME2, null, cv);
        SQ.close();
        Log.d("DATA", "SUBJECT, SUBJ INPUTED");
    }

    public List<Subjects> getSubjects() {
        SQLiteDatabase SQ = this.getWritableDatabase();

        List<Subjects> subjList = new LinkedList<Subjects>();

        String query = "SELECT  * FROM " + GradesData.DataInfo.TABLE_NAME2;

        Cursor CR = SQ.rawQuery(query, null);

        Subjects subjName = null;
        if (CR.moveToFirst()) {
            do {
                subjName = new Subjects(CR.getString(0));

                subjList.add(subjName);
            } while (CR.moveToNext());
        }

        return subjList;
    }
    public void deleteSubj (Subjects subjects){

        SQLiteDatabase SQ = this.getWritableDatabase();

        SQ.delete(GradesData.DataInfo.TABLE_NAME2,
                GradesData.DataInfo.SUBJ+" = ?",
                new String[] {String.valueOf(subjects.getSubj())});
        Log.d("delete", "deleted " + subjects.toString());

        // 3. close
        SQ.close();
    }
}
