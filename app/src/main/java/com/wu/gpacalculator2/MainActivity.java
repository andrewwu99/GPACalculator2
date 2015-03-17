package com.wu.gpacalculator2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ListActivity {

    private Button mAddButton;
    private ListView lv;
    Toast mToast;

    final DatabaseOps db = new DatabaseOps(this);
    final DataBaseOpsSubj db2 = new DataBaseOpsSubj(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddButton = (Button) findViewById(R.id.add_button);

        lv = getListView();

        List<Subjects> subjlist = db2.getSubjects();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjlist.size(); i++) {
            list.add(subjlist.get(i).toString());
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                showToast("You selected : " + item);
                Intent myIntent = new Intent(MainActivity.this, SubjectActivity.class);
                myIntent.putExtra("selected", item);
                startActivity(myIntent);
                }
        });

        mAddButton.setOnClickListener(new View.OnClickListener()

          {
              @Override
              public void onClick(View v) {
                  Intent myIntent = new Intent(MainActivity.this, AddAssignmentActivity.class);
                  startActivity(myIntent);
              }
         }

        );

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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }}

    public void onResume (){
        super.onResume();

        lv = getListView();

        List<Subjects> subjlist = db2.getSubjects();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjlist.size(); i++) {
            list.add(subjlist.get(i).toString());
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
            }
        });
    }
    private void showToast(String textToShow) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_SHORT);
        mToast.show();
    }
    }
