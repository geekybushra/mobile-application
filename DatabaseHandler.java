package com.example.logic.learningeng;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "theDatabase";

    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    private static final String TABLE_COURSES = "courses";
    private static final String KEY_C_NAME = "courseName";
    private static final String KEY_C_DESCRIBE = "courseDescribe";

    private static final String TABLE_ENROLLS = "enrolls";
    private static final String KEY_E_STUDENT = "enrollStudentId";
    private static final String KEY_E_COURSE = "enrollCourseId";
    private static final String KEY_E_LEVEL = "enrollLevel";
    private static final String KEY_E_SCORES = "enrollScores";

    private static final String TABLE_LECTURES = "lectures";
    private static final String KEY_L_NAME = "lectureName";
    private static final String KEY_L_DESCRIBE = "lectureDescribe";
    private static final String KEY_L_URL = "lectureUrl";
    private static final String KEY_L_LEVEL = "lectureLevel";
    private static final String KEY_COURSE_ID = "courseId";

    private static final String TABLE_TESTS = "tests";
    private static final String KEY_T_NAME = "testName";
    private static final String KEY_T_DESCRIBE = "testDescribe";
    private static final String KEY_LECTURE_ID = "lectureId";

    private static final String TABLE_QUESTIONS = "questions";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_FIRSTCHOICE = "firstChoice";
    private static final String KEY_SECONDCHOICE = "secondChoice";
    private static final String KEY_THIRDCHOICE = "thirdChoice";
    private static final String KEY_CORRECTCHOICE = "correctChoice";
    private static final String KEY_TEST_ID = "testId";

    private static final String TABLE_ACTIVITIES= "activities";
    private static final String KEY_A_STUDENT = "activeStudentId";
    private static final String KEY_A_TEST = "activeTestId";
    private static final String KEY_A_SCORE = "activeScore";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = " CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT " + ")";
        String CREATE_COURSES_TABLE = " CREATE TABLE " + TABLE_COURSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_C_NAME + " TEXT,"
                + KEY_C_DESCRIBE + " TEXT " + ")";
        String CREATE_ENROLLS_TABLE = " CREATE TABLE " + TABLE_ENROLLS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_E_STUDENT + " INTEGER,"
                + KEY_E_COURSE + " INTEGER,"
                + KEY_E_LEVEL + " TEXT,"
                + KEY_E_SCORES + " INTEGER " + ")";
        String CREATE_LECTURES_TABLE = " CREATE TABLE " + TABLE_LECTURES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_L_NAME + " TEXT,"
                + KEY_L_DESCRIBE + " TEXT,"
                + KEY_L_URL + " TEXT,"
                + KEY_L_LEVEL + " TEXT,"
                + KEY_COURSE_ID + " INTEGER " + ")" ;
        String CREATE_TESTS_TABLE = " CREATE TABLE " + TABLE_TESTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_T_NAME + " TEXT,"
                + KEY_T_DESCRIBE + " TEXT,"
                + KEY_LECTURE_ID + " INTEGER " + ")" ;
        String CREATE_QUESTIONS_TABLE = " CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_FIRSTCHOICE + " TEXT,"
                + KEY_SECONDCHOICE + " TEXT,"
                + KEY_THIRDCHOICE + " TEXT,"
                + KEY_CORRECTCHOICE + " TEXT,"
                + KEY_TEST_ID + " INTEGER " + ")" ;
        String CREATE_ACTIVITIES_TABLE = " CREATE TABLE " + TABLE_ACTIVITIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_A_STUDENT + " INTEGER,"
                + KEY_A_TEST + " INTEGER,"
                + KEY_A_SCORE + "  INTEGER "+ ")";

        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_COURSES_TABLE);
        db.execSQL(CREATE_ENROLLS_TABLE);
        db.execSQL(CREATE_LECTURES_TABLE);
        db.execSQL(CREATE_TESTS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_ACTIVITIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ENROLLS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LECTURES);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_TESTS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);

    }

    //Student functions
    public void addStudent(Student student) {

        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_STUDENTS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i = 0; i < columnNames.length; i++) {
            Log.e("TEST student tab col", columnNames[i]);
        }
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = " SELECT * FROM " + TABLE_STUDENTS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(KEY_ID, count);
        values.put(KEY_USERNAME, student.getUserName());
        values.put(KEY_PASSWORD, student.getPassword());
        values.put(KEY_EMAIL, student.getEmail());

        sqLiteDatabase.insert(TABLE_STUDENTS, null, values);

        sqLiteDatabase.close();

    }

    public String searchPass(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT userName, password FROM " + TABLE_STUDENTS;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(userName)) {
                    b = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return b;
    }

    public int getStudentId(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_ID + "," + KEY_USERNAME + " FROM " + TABLE_STUDENTS;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        int b;
        b = -1;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(1);
                if (a.equals(userName)) {
                    b = cursor.getInt(0);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return b;
    }

    // Courses functions
    void addCourses(Courses courses) {

        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_COURSES, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i = 0; i < columnNames.length; i++) {
            Log.e("TEST course tab columns", columnNames[i]);
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_C_NAME, courses.getCourse_name());
        values.put(KEY_C_DESCRIBE, courses.getCourse_describe());

        sqLiteDatabase.insert(TABLE_COURSES, null, values);
        sqLiteDatabase.close();

    }

    public List<Courses> getAllCourses() {
        List<Courses> coursesList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABLE_COURSES;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Courses courses = new Courses();
            courses.setId(Integer.parseInt(cursor.getString(0)));
            courses.setCourse_name(cursor.getString(1));
            courses.setCourse_describe(cursor.getString(2));
            coursesList.add(courses);
        }
        return coursesList;
    }

    public int getCourseId(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select id, courseName from " + TABLE_COURSES;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        int b;
        b = -1;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(1);
                if (a.equals(courseName)) {
                    b = cursor.getInt(0);
                    break;
                }
            } while (cursor.moveToNext());
        }
        Log.e("enrol save getCourseId", " ");

        return b;

    }
    // Enroll functions

    void saveEnrolls(Enrolls enrolls) {

        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_ENROLLS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i = 0; i < columnNames.length; i++) {
            Log.e(" enrol TEST tab columns", columnNames[i]);
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_E_STUDENT, enrolls.getStudentId());
        values.put(KEY_E_COURSE, enrolls.getCourseId());
        values.put(KEY_E_LEVEL, enrolls.getLevel());

        sqLiteDatabase.insert(TABLE_ENROLLS, null, values);
        sqLiteDatabase.close();
    }

    public String checkEnroll(int studentId, int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + TABLE_ENROLLS;
        Cursor cursor = db.rawQuery(query, null);
        String a, b, c;
        c = "not found";
        String stringSI = studentId + "";
        String stringCI = courseId + "";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(1);
                if (a.equals(stringSI)) {
                    do {
                        b = cursor.getString(2);
                        if (b.equals(stringCI)) {
                            c = cursor.getString(3);
                            break;
                        }
                    } while (cursor.moveToNext());
                    break;
                }
            } while (cursor.moveToNext());
        }
        return c;
    }
    // Lectures functions

    void addLecture(Lectures lectures){
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_LECTURES, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i= 0; i < columnNames.length; i++) {
            Log.e("TESTQQQQQ", columnNames[i]);
        }

        String courseName = lectures.getCourse_name();
        int theCourseId = getCourseId(courseName);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_L_NAME, lectures.getLecture_name());
        values.put(KEY_L_DESCRIBE, lectures.getLecture_describe());
        values.put(KEY_L_URL, lectures.getLecture_url());
        values.put(KEY_L_LEVEL, lectures.getLecture_level());
        values.put(KEY_COURSE_ID, theCourseId);
        sqLiteDatabase.insert(TABLE_LECTURES, null, values);
        sqLiteDatabase.close();
    }

    public List<Lectures> getLevelLectures(int theCourseId, String level){
        List<Lectures> lecturesList = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + TABLE_LECTURES + " where " + KEY_COURSE_ID + " = '" + theCourseId + "' AND " + KEY_L_LEVEL + " = '" + level+ "'" ;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Lectures lectures = new Lectures();
            lectures.setId(Integer.parseInt(cursor.getString(0)));
            lectures.setLecture_name(cursor.getString(1));
            lectures.setLecture_describe(cursor.getString(2));
            lectures.setLecture_url(cursor.getString(3));
            lectures.setLecture_level(cursor.getString(4));
            lecturesList.add(lectures);
        }
        return lecturesList;
    }


//Test functions

    public int getTheLectureId(String lectureName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select id, lectureName from "+TABLE_LECTURES;
        Cursor cursor = db.rawQuery(query , null);
        String a ;
        int b ;
        b = -1;
        if (cursor.moveToFirst()){
            do {
                a = cursor.getString(1);
                if(a.equals(lectureName)){
                    b = cursor.getInt(0);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }

    void addTests(Tests tests){
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_TESTS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i= 0; i < columnNames.length; i++) {
            Log.e("TESTS", columnNames[i]);
        }

        String lectureName = tests.getLecture_name();
        int theLectureId = getTheLectureId(lectureName);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_T_NAME, tests.getTest_name());
        values.put(KEY_T_DESCRIBE, tests.getTest_describe());
        values.put(KEY_LECTURE_ID, theLectureId);
        sqLiteDatabase.insert(TABLE_TESTS, null, values);
        sqLiteDatabase.close();
    }

    public List<Tests> getSomeTests(int theLectureId){
        List<Tests> testsList = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + TABLE_TESTS + " where " + KEY_LECTURE_ID + " = '" + theLectureId + "'" ;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Tests tests = new Tests();
            tests.setId(Integer.parseInt(cursor.getString(0)));

            tests.setTest_name(cursor.getString(1));

            tests.setTest_describe(cursor.getString(2));

            testsList.add(tests);
        }
        return testsList;
    }
    // Questions functions

    public int getTheTestId(String testName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select id, testName from "+TABLE_TESTS;
        Cursor cursor = db.rawQuery(query , null);
        String a ;
        int b ;
        b = -1;
        if (cursor.moveToFirst()){
            do {
                a = cursor.getString(1);
                if(a.equals(testName)){
                    b = cursor.getInt(0);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }

    void addQuestion(Questions questions){
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_QUESTIONS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i= 0; i < columnNames.length; i++) {
            Log.e("TESTQQQQQ", columnNames[i]);
        }

        String testName = questions.getTestName();
        int theTestId = getTheTestId(testName);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, questions.getQuestion());
        values.put(KEY_FIRSTCHOICE, questions.getFirstChoice());
        values.put(KEY_SECONDCHOICE, questions.getSecondChoice());
        values.put(KEY_THIRDCHOICE, questions.getThirdChoice());
        values.put(KEY_CORRECTCHOICE, questions.getCorrectChoice());
        values.put(KEY_TEST_ID, theTestId);
        sqLiteDatabase.insert(TABLE_QUESTIONS, null, values);
        sqLiteDatabase.close();

    }

    public List<Questions> getSomeQuestions(int theTestId){
        List<Questions> questionsList = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + TABLE_QUESTIONS + " where " + KEY_TEST_ID + " = '" + theTestId + "'" ;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            Questions questions = new Questions();
            questions.setId(Integer.parseInt(cursor.getString(0)));
            questions.setQuestion(cursor.getString(1));
            questions.setFirstChoice(cursor.getString(2));
            questions.setSecondChoice(cursor.getString(3));
            questions.setThirdChoice(cursor.getString(4));
            questions.setCorrectChoice(cursor.getString(5));
            questionsList.add(questions);
        }
        return questionsList;
    }
// Activity functions

    void saveActivity(Activities activity) {

        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_ACTIVITIES, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

        for (int i = 0; i < columnNames.length; i++) {
            Log.e(" activ TEST tab columns", columnNames[i]);
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_A_STUDENT, activity.getStudentId());
        values.put(KEY_A_TEST, activity.getTestId());
        values.put(KEY_A_SCORE, activity.getScore());
        sqLiteDatabase.insert(TABLE_ACTIVITIES, null, values);
        sqLiteDatabase.close();

    }
    public List<Activities> getActivities(int studentId) {
        List<Activities> activitiesList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABLE_ACTIVITIES + " WHERE " + KEY_A_STUDENT + " = '" + studentId +  "'";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Activities activities = new Activities();
            activities.setId(Integer.parseInt(cursor.getString(0)));
            activities.setStudentId(Integer.parseInt(cursor.getString(1)));
            activities.setTestId(Integer.parseInt(cursor.getString(2)));
            activities.setScore(Integer.parseInt(cursor.getString(3)));
            activitiesList.add(activities);
        }
        return activitiesList;
    }

    public String checkActivity(int studentId, int testId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + TABLE_ACTIVITIES;
        Cursor cursor = db.rawQuery(query, null);
        String a, b, c;
        c = "not found";
        String stringStdI = studentId + "";
        String stringTstI = testId + "";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(1);
                if (a.equals(stringStdI)) {
                    //
                    do {
                        b = cursor.getString(2);

                        if (b.equals(stringTstI)) {
                            c = cursor.getString(0);
                            break;
                        }
                    } while (cursor.moveToNext());
                    //
                    break;
                }
            } while (cursor.moveToNext());
        }
        return c;
    }

    void updateActivityScore(int activityId, int score){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ACTIVITIES + " SET " + KEY_A_SCORE + " = '" + score + "' WHERE " + KEY_ID + " ='" + activityId + "'");
        db.close();

    }

    int getScoreSum(int studentId){

        SQLiteDatabase db = this.getReadableDatabase();
        int sum;
        Cursor c;
        c = db.rawQuery("SELECT SUM( " + KEY_A_SCORE + " ) FROM " + TABLE_ACTIVITIES +" WHERE "+ KEY_A_STUDENT + " = " + studentId+ " ;", null);
        if(c.moveToFirst())
            sum = c.getInt(0);
        else
            sum = -1;
        c.close();
        db.close();

        return sum;
    }

    void updateEnrollScore(int studentId){
        int sumScore = getScoreSum(studentId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ENROLLS + " SET " + KEY_E_SCORES + " = '" + sumScore + "' WHERE " + KEY_E_STUDENT + " ='" + studentId + "'");

        db.close();
    }

    void updateForNextLevel(int studentId , String level){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ENROLLS + " SET " + KEY_E_LEVEL + " = '" + level + "' WHERE " + KEY_E_STUDENT + " ='" + studentId + "'");
        db.execSQL("UPDATE " + TABLE_ENROLLS + " SET " + KEY_E_SCORES + " = " + 0 + " WHERE " + KEY_E_STUDENT + " ='" + studentId + "'");
        db.close();

    }

    void updateForNxtLvl(int studentId ) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE  FROM " + TABLE_ACTIVITIES + " WHERE " + KEY_A_STUDENT + " ='" + studentId + "'");
        db.close();

    }


}
