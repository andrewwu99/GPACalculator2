package com.wu.gpacalculator2;

/**
 * Created by Swaggiott on 10/3/2015.
 */
public class GradeItems {
    private String mSubj;
    private String mName;
    private float mMarks;
    private float mMarksPos;
    private float mWeightage;


    public GradeItems(String subj, String name, float marks, float marksPos, float weightage) {
        mSubj = subj;
        mName = name;
        mMarks = marks;
        mMarksPos = marksPos;
        mWeightage = weightage;
    }


    public String getSubj() {
        return mSubj;
    }

    public void setSubj(String subj) {
        mName = subj;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getMarks() {
        return mMarks;
    }

    public void setMarks(float marks) {
        mMarks = marks;
    }

    public float getMarksPos() {
        return mMarksPos;
    }

    public void setMarksPos(float marksPos) {
        mMarksPos = marksPos;
    }

    public float getWeightage() {
        return mWeightage;
    }

    public void setWeightage(float weightage) {
        mWeightage = weightage;
    }
}
