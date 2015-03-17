package com.wu.gpacalculator2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SubjectActivity extends ListActivity {
    private ListView lv;

    final DatabaseOps db = new DatabaseOps(this);
    final DataBaseOpsSubj db2 = new DataBaseOpsSubj(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = getListView();

        List<GradeItems> gradelist = db.getAllGrade();

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<GradeItems> listGrades = new ArrayList<GradeItems>();


        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gradelist);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            }
        });


        //ArrayList <Subjects> mSubjList = {};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume (){
        super.onResume();

        lv = getListView();

        List<Subjects> subjlist = db2.getSubjects();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjlist.size(); i++) {
            list.add(subjlist.get(i).toString());
        }
    }
}
