package com.example.logic.learningeng;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;
import java.util.List;
import com.google.android.youtube.player.YouTubePlayer.Provider;
// this class view the video for the lecture and also provide a button so we can click and go to test activity

public class LectureContent extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    TextView lectureTitleV;
    TextView lectureContentView;
    List<Lectures> lectureList;
    DatabaseHandler database;
    String lectureTitle;
    int courseId;
    int lectureId;
    Spinner testSpinner;
    TextView testDetailView;
    List<Tests> testsList;
    ArrayAdapter<String> testAdapter;
    ArrayList<String> testNameList;
    GlobalVar globalVar;
    String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_content);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        database = new DatabaseHandler(this);
        globalVar = (GlobalVar)getApplication();

        lectureTitleV = (TextView)findViewById(R.id.lectureTitle);
        lectureContentView = (TextView)findViewById(R.id.lectureContent);
        lectureTitle = getIntent().getStringExtra("Lecture Title");
        courseId = Integer.parseInt(getIntent().getStringExtra("Course"));
        lectureTitleV.setText(lectureTitle);
        String level = globalVar.getLevel();
        lectureList = database.getLevelLectures(courseId,level);
        viewLectureContent(lectureTitle);

        testSpinner = (Spinner) findViewById(R.id.chooseTest);
        testDetailView = (TextView)findViewById(R.id.testDetail);
        testsList = database.getSomeTests(lectureId);

        testAdapter = new ArrayAdapter<String>(LectureContent.this, android.R.layout.simple_spinner_dropdown_item, takeTestList());
        testSpinner.setAdapter(testAdapter);
        testSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String itemName = (String) testSpinner.getSelectedItem();
                updateTestDetail(itemName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Button btn = (Button) findViewById(R.id.goToTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LectureContent.this , TestActivity.class);
                String itemName = (String) testSpinner.getSelectedItem();
                intent.putExtra("testName",itemName);

                startActivity(intent);

            }
        });

    }

    // the lecture part functions
    void viewLectureContent(String lectureName) {
        for (Lectures c : lectureList) {
            if (c.getLecture_name().equals(lectureName)) {
                lectureId = c.getId();
                String lectureContent = "" + c.getLecture_describe();
                url = c.getLecture_url();
                lectureContentView.setText(lectureContent);
            }
        }

    }

    // the test part functions
    private ArrayList takeTestList() {
        testNameList = new ArrayList<String>();
        for (Tests t : testsList) {
            String testName = "" + t.getTest_name();
            testNameList.add(testName);
        }

        return testNameList;
    }

    void updateTestDetail(String testName) {
        for (Tests t : testsList) {
            if (t.getTest_name().equals(testName)) {
                String testDetail = "" + t.getTest_describe();
                testDetailView.setText(testDetail);
            }
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            Log.e("URL",url);
            player.loadVideo(url);
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
