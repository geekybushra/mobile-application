package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    GlobalVar globalVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        databaseHandler = new DatabaseHandler(this);

        databaseHandler.addStudent(new Student("bo","bo","bo"));

    }
    public void onSignInClick(View view){
        globalVar = (GlobalVar)getApplication();
        EditText myUserName = (EditText)findViewById(R.id.editText);
        String myUserNameStr = String.valueOf(myUserName.getText());
        EditText myPassword = (EditText)findViewById(R.id.editText2);
        String myPasswordStr = myPassword.getText().toString();
        String pass = databaseHandler.searchPass(myUserNameStr);

        if(myPasswordStr.equals(pass)){
            Intent i = new Intent(SignIn.this, ChooseCourse.class);
            databaseHandler.getStudentId(myUserNameStr);
            globalVar.setStudentName(myUserNameStr);
            globalVar.setStudentId(databaseHandler.getStudentId(myUserNameStr));
            i.putExtra("userName",myUserNameStr);
            startActivity(i);
        }else {
            Toast tempt= Toast.makeText(SignIn.this, "Username and Password don't match", Toast.LENGTH_SHORT);
            tempt.show();

        }

    }

    public void onSignUpPageClick(View view){
        Intent i = new Intent(SignIn.this, SignUp.class);
        startActivity(i);

    }
}
