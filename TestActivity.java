package com.example.logic.learningeng;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;
import static android.graphics.Color.luminance;
import static android.graphics.Color.parseColor;

public class TestActivity extends AppCompatActivity {

    TextView score;
    TextView questionView;
    Button choice1btn;
    Button choice2btn;
    Button choice3btn;
    TextView time;
    int questionNumber = 0;
    int scoreNo;
    String correctChoice;
    DatabaseHandler databaseHelper;
    CountDownTimer countDownTimer;
    int timeInSeconds = 10 ;
    String prevTestName;
    int prevTestId;
    GlobalVar globalVar;
    int studentId;
    String Qid;
    int wrongAnswers = 0;
    int courseId;
    boolean t = false;
    List<Questions> questions;
    int limit;
    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        databaseHelper = new DatabaseHandler(this);
        globalVar = (GlobalVar)getApplication();
        studentId = globalVar.getStudentId();
        score = (TextView) findViewById(R.id.score);
        questionView = (TextView) findViewById(R.id.question);
        choice1btn = (Button) findViewById(R.id.choice1);
        choice2btn = (Button) findViewById(R.id.choice2);
        choice3btn = (Button) findViewById(R.id.choice3);
        time = (TextView) findViewById(R.id.time);
        scoreNo = 0;
        correctChoice = "";

        Bundle prevActivityData = getIntent().getExtras();
        prevTestName = prevActivityData.getString("testName");
        prevTestId = databaseHelper.getTheTestId(prevTestName);

        if (prevTestName.equals("Intro Test Grammar")){
            limit = 14;
        }else {
            limit = 5;
        }



        questions = databaseHelper.getSomeQuestions(prevTestId);
        for(Questions q : questions){
            questionNumber =  q.getId();
            if (t){
                break;
            }
        }

        updateQuestion();
        // this line brings the first question to test activity


        choice1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1btn.getText().equals(correctChoice)) {
                    scoreNo = scoreNo + 1; // score is incremented if answer is correct
                    updateScore(scoreNo);
                    choice1btn.setBackgroundColor(Color.parseColor("#ff669900"));
                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                } else {
                    wrongAnswers = wrongAnswers + 1;
                    choice1btn.setBackgroundColor(Color.parseColor("#ffff4444"));
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                }
                choice1btn.setEnabled(false);// all the buttons are disabled after clicking first button
                choice2btn.setEnabled(false);
                choice3btn.setEnabled(false);
            }
        });
        // these line execute if the first button is pressed and it checks the answer

        choice2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2btn.getText().equals(correctChoice)) {
                    scoreNo = scoreNo + 1;// score is incremented if answer is correct
                    updateScore(scoreNo);
                    choice2btn.setBackgroundColor(Color.parseColor("#ff669900"));

                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                } else {
                    wrongAnswers = wrongAnswers + 1;
                    choice2btn.setBackgroundColor(Color.parseColor("#ffff4444"));
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                }
                choice1btn.setEnabled(false);// all the buttons are disabled after clicking second button
                choice2btn.setEnabled(false);
                choice3btn.setEnabled(false);
            }
        });
        // these line execute if the second button is pressed and it checks the answer

        choice3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3btn.getText().equals(correctChoice)) {
                    scoreNo = scoreNo + 1;// score is incremented if answer is correct
                    updateScore(scoreNo);
                    choice3btn.setBackgroundColor(Color.parseColor("#ff669900"));

                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                } else {
                    wrongAnswers = wrongAnswers + 1;
                    choice3btn.setBackgroundColor(Color.parseColor("#ffff4444"));
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                }
                choice1btn.setEnabled(false);// all the buttons are disabled after clicking third button
                choice2btn.setEnabled(false);
                choice3btn.setEnabled(false);
            }

        });
        // these line execute if the third button is pressed and it checks the answer



    }
    //this method brings all the information related to a question and diplay it on test activity
    private void updateQuestion(){

        //this line gets size of the list of questions to know how many questions are there

        for (Questions c : questions) {

                if ( c.getId() == questionNumber) {

                    Qid = "" + c.getId();
                    String question = "" + c.getQuestion();

                    questionView.setText(question);
                    String choice1 = "" + c.getFirstChoice();

                    choice1btn.setText(choice1);
                    String choice2 = "" + c.getSecondChoice();

                    choice2btn.setText(choice2);
                    String choice3 = "" + c.getThirdChoice();

                    choice3btn.setText(choice3);

                    correctChoice = "" + c.getCorrectChoice();

            }

        }
        questionNumber = questionNumber - 1;
        num = num + 1;
        start();

        choice1btn.setEnabled(true);// buttons are enabled when the question is updated
        choice2btn.setEnabled(true);
        choice3btn.setEnabled(true);
        choice1btn.setBackgroundColor(Color.parseColor("#ffffbb33"));// colors go back to yellow when question are updated
        choice2btn.setBackgroundColor(Color.parseColor("#ffffbb33"));
        choice3btn.setBackgroundColor(Color.parseColor("#ffffbb33"));
    }

    // this method updates the score if the answer is correct
    private void updateScore(int scoreNo){

        score.setText(""+ scoreNo);
    }

    // this is the timer which starts with each question
    private void start(){
        countDownTimer = new CountDownTimer(timeInSeconds*1000,1000){

            @Override
            public void onTick(long millisUntilFinished ) {
                time.setText(millisUntilFinished/1000+" seconds");
            }

            @Override
            public void onFinish() {
                if(num > limit){
                    // if its the last question then another activity appear

                    String finalScore = score.getText().toString();
// we check is prevtest is intro then we directly go to lectures but if not the code below runs

                    if(prevTestName.equals("Intro Test Grammar")) {

                        if (scoreNo >= 11) {
                            globalVar.setLevel("advanced");
                        }

                        if (scoreNo <= 5) {
                            globalVar.setLevel("beginner");
                        }

                        if(scoreNo < 11 && scoreNo > 5){
                            globalVar.setLevel("intermediate");
                        }

                        courseId = globalVar.getCourseId();
                        String levl = globalVar.getLevel();
                        Enrolls enrolls = new Enrolls(studentId,courseId,levl);
                        databaseHelper.saveEnrolls(enrolls);
                        Intent intent = new Intent(TestActivity.this , LecturesList.class);
                        startActivity(intent);
                    }else {

                        String checkActive = databaseHelper.checkActivity(studentId,prevTestId);
                        Intent intent = new Intent(TestActivity.this , ResultView.class);
                        intent.putExtra("finalScore",finalScore);
                        intent.putExtra("test name",prevTestName);
                        intent.putExtra("wrong answers",""+wrongAnswers);

                        if (checkActive.equals("not found")) {
                            databaseHelper.saveActivity(new Activities(studentId, prevTestId, scoreNo));

                        } else {
                            int activityId = Integer.parseInt(checkActive);
                            int score = Integer.parseInt(finalScore);
                            databaseHelper.updateActivityScore(activityId, score);

                        }
                        databaseHelper.updateEnrollScore(studentId);
                        startActivity(intent);
                    }
                }else{
                    updateQuestion();
                }
            }

        };
        countDownTimer.start();
    }

}


