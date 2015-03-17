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
 * Created by Swaggiott on 3/3/2015.
 */
public class DatabaseOps extends SQLiteOpenHelper{

    public static final int database_version = 1;


    public DatabaseOps(Context context) {
        super(context, GradesData.DataInfo.TABLE_NAME, null, database_version);
        Log.d("DATA", "DATADONE");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + GradesData.DataInfo.TABLE_NAME+ " ( "+
                GradesData.DataInfo.SUBJ + " TEXT , " +
                GradesData.DataInfo.NAME + " TEXT , " +
                GradesData.DataInfo.SCORE + " TEXT , " +
                GradesData.DataInfo.MAX_SCORE + " TEXT , " +
                GradesData.DataInfo.WEIGHTAGE + " TEXT " +
                ");";

        db.execSQL(CREATE_QUERY);
        Log.d("DATA", "DATADONE, TABLE CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GradesData.DataInfo.TABLE_NAME);

        onCreate(db);
    }

    public void grade (String subj, String name, String score, String maxscore, String weightage) {
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(GradesData.DataInfo.SUBJ, subj);
        cv.put(GradesData.DataInfo.NAME, name);
        cv.put(GradesData.DataInfo.SCORE, score);
        cv.put(GradesData.DataInfo.MAX_SCORE, maxscore);
        cv.put(GradesData.DataInfo.WEIGHTAGE, weightage);

        SQ.insert(GradesData.DataInfo.TABLE_NAME, null, cv);
        SQ.close();
        Log.d("DATA", "DATADONE, DATA INPUTED Subj = " + subj);
    }

    public GradeItems getGrade(String name){
        SQLiteDatabase SQ = this.getReadableDatabase();
        String [] columns = {GradesData.DataInfo.SUBJ, GradesData.DataInfo.NAME, GradesData.DataInfo.SCORE, GradesData.DataInfo.MAX_SCORE, GradesData.DataInfo.WEIGHTAGE};

        Cursor CR = SQ.query(GradesData.DataInfo.TABLE_NAME, columns, " name = ", null, null, null, null);

        if (CR != null)
            CR.moveToFirst();

        GradeItems grade = new GradeItems(CR.getString(0), CR.getString(1),Float.valueOf(CR.getString(2)),Float.valueOf(CR.getString(3)), Float.valueOf(CR.getString(4)));

        return grade;
    }

    public List <GradeItems> getAllGrade(){
        List<GradeItems> gradesList= new LinkedList<GradeItems>();

        String query= "SELECT  * FROM " + GradesData.DataInfo.TABLE_NAME;

        SQLiteDatabase SQ = this.getWritableDatabase();
        Cursor CR = SQ.rawQuery(query, null);

        GradeItems grade = null;
        if (CR.moveToFirst()){
            do{
                grade = new GradeItems(CR.getString(0), CR.getString(1),Float.valueOf(CR.getString(2)),Float.valueOf(CR.getString(3)),Float.valueOf(CR.getString(4)));

                gradesList.add(grade);
            } while (CR.moveToNext());
        }
    return gradesList;
    }

    public int updateGrade( GradeItems grade){
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put(GradesData.DataInfo.SUBJ, grade.getSubj());
        cv.put(GradesData.DataInfo.SCORE, grade.getMarks());
        cv.put(GradesData.DataInfo.MAX_SCORE, grade.getMarksPos());
        cv.put(GradesData.DataInfo.WEIGHTAGE, grade.getWeightage());

        int i = SQ.update(GradesData.DataInfo.TABLE_NAME,
                cv,
                GradesData.DataInfo.NAME+" = ?",
                new String[] { String.valueOf(grade.getName())}
                );

        SQ.close();

        return i;
    }

    public void deleteGrade (GradeItems grade){

        SQLiteDatabase SQ = this.getWritableDatabase();

        SQ.delete(GradesData.DataInfo.TABLE_NAME, //table name
                GradesData.DataInfo.NAME+" = ?",  // selections
                new String[] { String.valueOf(grade.getName()) }); //selections args

        // 3. close
        SQ.close();
    }




    }


