package com.wu.gpacalculator2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddAssignmentActivity extends ActionBarActivity implements OnItemSelectedListener{

    final Context context = this;
    private Button mDoneButton;
    private Button mResButton;
    private Button mAddSubjButton;
    private Button mDeleteSubjButton;
    private float currentPercentage = 0;
    Toast mToast;
    Spinner spinner;



    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(parent.getContext(), "You selected nothing lolz ",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);


        final Intent myIntent = getIntent();


        mDoneButton = (Button) findViewById(R.id.done_button);
        mResButton = (Button) findViewById(R.id.reset_button);
        mAddSubjButton = (Button) findViewById(R.id.addSubj);
        mDeleteSubjButton = (Button) findViewById(R.id.deleteSubj);
        final DatabaseOps db = new DatabaseOps(this);
        final DataBaseOpsSubj db2 = new DataBaseOpsSubj(this);
        final TextView mCurrent = (TextView) findViewById(R.id.current);
        final Spinner spinner = (Spinner) findViewById(R.id.subjects);

        //create array for spinners
        final List subjListx =db2.getSubjects();

        ArrayList<String> subjList = new ArrayList<String>();

        for (int q = 0; q < subjListx.size(); q++) subjList.add(subjListx.get(q).toString());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(dataAdapter);

        final String userSelect = spinner.getSelectedItem().toString();





        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the edittext input strings
                final EditText assName = (EditText) findViewById(R.id.assgmnt);
                final EditText assScores = (EditText) findViewById(R.id.scoresObtained);
                final EditText assScoresPos = (EditText) findViewById(R.id.scoresTotal);
                final EditText assWeight = (EditText) findViewById(R.id.weight);

                final String assignmentName = assName.getText().toString().trim();

                List<GradeItems> gradeList=  db.getAllGrade();

                for (int i = 0; i < gradeList.size(); i++){
                    currentPercentage += Float.valueOf(gradeList.toString());
                    Log.d ("Current Percentage", "Percentage is"+currentPercentage);
                }


                //validity check, if valid convert edittextfields to floats
                if (assignmentName.matches("") || assScores.getText().toString().matches("") || assScoresPos.getText().toString().matches("") || assWeight.getText().toString().matches("")) {
                    showToast("All fields are compulsory");
                } else {

                    final float assignmentScoresfloat = Float.valueOf(assScores.getText().toString());
                    final float assignmentScoresPosfloat = Float.valueOf(assScoresPos.getText().toString());
                    final float assignmentWeightfloat = Float.valueOf(assWeight.getText().toString());

                    //more checks
                    if (assignmentScoresfloat == 0) {
                        showToast("Please enter a valid Maximum Score");
                    } else if (assignmentWeightfloat == 0) {
                        showToast("Please enter a legit weightage");
                    } else if (assignmentScoresfloat > assignmentScoresPosfloat) {
                        showToast("Don't lie about the Scores");
                    } else if ((currentPercentage + assignmentWeightfloat) > 100) {
                        showToast("Don't lie about the weightage");
                    } else {
                        //add to data

                        db.grade(userSelect, assignmentName, String.valueOf(assScores.getText()), String.valueOf(assScoresPos.getText()), String.valueOf(assWeight.getText()));

                        //reset the fields
                        assName.setText("", TextView.BufferType.EDITABLE);
                        assScores.setText("", TextView.BufferType.EDITABLE);
                        assScoresPos.setText("", TextView.BufferType.EDITABLE);
                        assWeight.setText("", TextView.BufferType.EDITABLE);
                    }
                    ;
                };
                finish();
            }
        });

        mResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText assName = (EditText) findViewById(R.id.assgmnt);
                final EditText assScores = (EditText) findViewById(R.id.scoresObtained);
                final EditText assScoresPos = (EditText) findViewById(R.id.scoresTotal);
                final EditText assWeight = (EditText) findViewById(R.id.weight);

                //reset the fields
                assName.setText("", TextView.BufferType.EDITABLE);
                assScores.setText("", TextView.BufferType.EDITABLE);
                assScoresPos.setText("", TextView.BufferType.EDITABLE);
                assWeight.setText("", TextView.BufferType.EDITABLE);
            }
        });

        mAddSubjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextAddSubj);

                AlertDialog.Builder builder = alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db2.subjects(userInput.getText().toString());

                                        final List subjListx2 =db2.getSubjects();

                                        ArrayList <String> subjList3 = new ArrayList<>();

                                        for (int p = 0; p < subjListx2.size(); p++) subjList3.add(subjListx2.get(p).toString());
                                        Log.d("ubjlisy", "klksg" + subjList3);
                                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(AddAssignmentActivity.this, android.R.layout.simple_spinner_item, subjList3);
                                        spinner.setAdapter(dataAdapter2);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }});

            mDeleteSubjButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.promtdelete, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(promptsView);
                        final Spinner spinner2 = (Spinner) promptsView.findViewById (R.id.spinnerDeleteSubj);

                        final List <Subjects> subjListx = db2.getSubjects();
                        ArrayList <String> subjList2 = new ArrayList<>();

                        for (int p = 0; p < subjListx.size(); p++) subjList2.add(subjListx.get(p).toString());

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(AddAssignmentActivity.this, android.R.layout.simple_spinner_item, subjList2);
                        spinner2.setAdapter(dataAdapter2);

                        final String userSelect2 = spinner.getSelectedItem().toString();
                        final Subjects userSelectSubj = new Subjects(userSelect2);
                    AlertDialog.Builder builder = alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            db2.deleteSubj(userSelectSubj);

                                            final List subjListx2 =db2.getSubjects();

                                            ArrayList <String> subjList3 = new ArrayList<>();

                                            for (int p = 0; p < subjListx2.size(); p++) subjList3.add(subjListx2.get(p).toString());
                                            Log.d("ubjlisy", "klksg" + subjList3);
                                            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(AddAssignmentActivity.this, android.R.layout.simple_spinner_item, subjList3);
                                            spinner2.setAdapter(dataAdapter2);
                                            spinner.setAdapter(dataAdapter2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }


                });

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_assignment, menu);
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
    private void showToast(String textToShow) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_SHORT);
        mToast.show();
    }


}
