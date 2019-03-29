package com.example.logic.learningeng;

import android.app.Activity;
import android.os.Bundle;

public class Tests {
    int id;
    String test_name;
    String test_describe;
    String lecture_name;

    public Tests() {
    }

    public Tests( String test_name, String test_describe, String lecture_name) {
        this.test_name = test_name;
        this.test_describe = test_describe;
        this.lecture_name = lecture_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_describe() {
        return test_describe;
    }

    public void setTest_describe(String test_describe) {
        this.test_describe = test_describe;
    }

    public String getLecture_name() {
        return lecture_name;
    }

    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }
}
