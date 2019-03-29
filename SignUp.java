package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    DatabaseHandler databaseHandler ;
    GlobalVar globalVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        databaseHandler = new DatabaseHandler(this);
        globalVar = (GlobalVar)getApplication();
    }

    public void onSignUpClick(View view){
        EditText myUserNames = (EditText)findViewById(R.id.username);
        EditText myPass1 = (EditText)findViewById(R.id.password);
        EditText myPass2 = (EditText)findViewById(R.id.passconfirm);
        EditText myEmail = (EditText)findViewById(R.id.email);

        String myUserNamesStr = myUserNames.getText().toString();
        String myPass1Str = myPass1.getText().toString();
        String myPass2Str = myPass2.getText().toString();
        String myEmailStr = myEmail.getText().toString();


        if(!myPass1Str.equals(myPass2Str)){

            Toast pass= Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
            pass.show();
        }
        else {

            Student s = new Student();

            s.setUserName(myUserNamesStr);
            s.setPassword(myPass1Str);
            s.setEmail(myEmailStr);
            databaseHandler.addStudent(s);
            Intent i = new Intent(SignUp.this, ChooseCourse.class);
            i.putExtra("userName",myUserNamesStr);
            globalVar.setStudentId(databaseHandler.getStudentId(myUserNamesStr));
            globalVar.setStudentName(myUserNamesStr);
            startActivity(i);
        }

    }

}
