package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    TextView personalInfo;
    TextView studyInfo;
    GlobalVar globalVar;
    DatabaseHandler databaseHandler;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        personalInfo = (TextView) findViewById(R.id.personalInfo);
        studyInfo = (TextView) findViewById(R.id.studyInfo);
        globalVar = (GlobalVar) getApplication();
        databaseHandler = new DatabaseHandler(this);
        studentId = globalVar.getStudentId();

        String stdName = globalVar.getStudentName();
        String stdLevel = globalVar.getLevel();
        String courseName = globalVar.getCourseName();
        int scoreSum = databaseHandler.getScoreSum(studentId);
        personalInfo.setText("Hello, " + stdName + " , I hope you are enjoying your English learning journey with us! ");
        int remaining = 30 - scoreSum;
        studyInfo.setText("     Your current level of the course: " + courseName + " is: (" + stdLevel + "), and your total score is: (" +
                scoreSum + ") out of 30. In order for you to finish this level and get to the next level, you must get the remaining " + remaining + " scores");

    }

    public void OnGoToCourseBtn(View view) {
        Intent intent = new Intent(HomePage.this, ChooseCourse.class);
        startActivity(intent);

    }
}
