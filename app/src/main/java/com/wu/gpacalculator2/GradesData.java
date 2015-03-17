package com.wu.gpacalculator2;

import android.provider.BaseColumns;

/**
 * Created by Swaggiott on 3/3/2015.
 */
public class GradesData {
    public String mName;
    public float mScore;
    public float mTotalScore;
    public float mWeightage;

    public GradesData(String name, float score, float totalScore, float weightage) {
        mName = name;
        mScore = score;
        mTotalScore = totalScore;
        mWeightage = weightage;
    }

    public static abstract class DataInfo implements BaseColumns
    {
        public static final String SUBJ = "subject";
        public static final String NAME = "name";
        public static final String SCORE = "score";
        public static final String MAX_SCORE = "max_score";
        public static final String WEIGHTAGE = "weightage";

        public static final String TABLE_NAME2 = "subject_name";
        public static final String TABLE_NAME = "grade_name";
    }
}
