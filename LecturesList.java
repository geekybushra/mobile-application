package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
// this class view a list of lecture according to a students level
public class LecturesList extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView myListView;
    ListAdapter myListAdapter;
    List<Lectures> lectureList;
    DatabaseHandler database;
    GlobalVar globalVar;
    int prevCourseId;
    String prevLevel;
    TextView levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_list);

        database = new DatabaseHandler(this);
        globalVar = (GlobalVar)getApplication();

        levelList = (TextView)findViewById(R.id.levelList);

        myListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, takeLecturesList());

        myListView = (ListView)findViewById(R.id.myListView);

        myListView.setAdapter(myListAdapter);

        myListView.setOnItemClickListener(this);

    }

    private ArrayList takeLecturesList() {
        prevCourseId = globalVar.getCourseId();
        prevLevel = globalVar.getLevel();
        levelList.setText("Level: "+prevLevel);
        lectureList = database.getLevelLectures(prevCourseId,prevLevel);

        ArrayList<String> lecturesNameList = new ArrayList<String>();
        for (Lectures c : lectureList) {
            String lectureName = "" + c.getLecture_name();
            lecturesNameList.add(lectureName);

        }
        return lecturesNameList;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView)view;
        String text = (String) tv.getText();
        Intent intent = new Intent(LecturesList.this,LectureContent.class);
        intent.putExtra("Lecture Title",text);
        intent.putExtra("Course",""+prevCourseId );
        startActivity(intent);

    }
}

