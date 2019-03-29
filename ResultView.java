package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
// this class is linked to an activity which shows the student her/his score in the test
//and also shows the student her/his total score for her current level

public class ResultView extends AppCompatActivity{

DatabaseHandler databaseHandler;
    GlobalVar globalVar;
    int studentId;
    TextView viewWAns;
    TextView viewScore;
    String finalScore;
    String testName;
    String wrongAnswers;
    Button goLecBtn;
    String level;
    int totalScore;
    TextView hiName;
    Button goHomePgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        databaseHandler = new DatabaseHandler(this);
        globalVar = (GlobalVar)getApplication();
        studentId = globalVar.getStudentId();
        level = globalVar.getLevel();
        viewWAns = (TextView)findViewById(R.id.wrongAnswrs);
        viewScore = (TextView)findViewById(R.id.yourScore);
        goLecBtn = (Button)findViewById(R.id.goLectures) ;
        hiName = (TextView)findViewById(R.id.hi);
        goHomePgbtn = (Button)findViewById(R.id.goHomePage) ;
        finalScore = getIntent().getStringExtra("finalScore");
        testName = getIntent().getStringExtra("test name");
        wrongAnswers = getIntent().getStringExtra("wrong answers");
        totalScore = databaseHandler.getScoreSum(studentId);
        hiName.setText("Hi, "+globalVar.getStudentName());
        int missedAnswers = 6 - ( Integer.parseInt(finalScore) + Integer.parseInt(wrongAnswers) );
        viewScore.setText("Your score is: " + finalScore + " out of 6, for: " + testName+". and your total score for this level is: "+totalScore+" out of 30." );
        viewWAns.setText("You have got: " + wrongAnswers + " wrong answers, and you have missed " + missedAnswers + " questions." );

        goLecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalScore == 30) {
                    Intent intent;
                    if(level.equals("beginner")){
                        globalVar.setLevel("intermediate");
                        intent = new Intent(ResultView.this, LecturesList.class);
                        databaseHandler.updateForNextLevel(studentId,"intermediate");
                        databaseHandler.updateForNxtLvl(studentId);
                    }else if (level.equals("intermediate")){
                        globalVar.setLevel("advanced");
                        intent = new Intent(ResultView.this, LecturesList.class);
                        databaseHandler.updateForNextLevel(studentId,"advanced");
                        databaseHandler.updateForNxtLvl(studentId);

                    }else{
                        intent = new Intent(ResultView.this, ChooseCourse.class);
                    }
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(ResultView.this, LecturesList.class);
                    startActivity(intent);
                }
            }});

        goHomePgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalScore == 30) {
                    if(level.equals("beginner")){
                        globalVar.setLevel("intermediate");
                        databaseHandler.updateForNextLevel(studentId,"intermediate");
                        databaseHandler.updateForNxtLvl(studentId);
                    }else if (level.equals("intermediate"))
                    {
                        globalVar.setLevel("advanced");
                        databaseHandler.updateForNextLevel(studentId,"advanced");
                        databaseHandler.updateForNxtLvl(studentId);
                    }
                }
                Intent intent = new Intent(ResultView.this, HomePage.class);
                startActivity(intent);

            }});


    }


}
