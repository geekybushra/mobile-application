package com.example.logic.learningeng;

import android.app.Activity;
import android.os.Bundle;

public class Courses {
    int id;
    String course_name;
    String course_describe;

    public Courses() {
    }

    public Courses(String course_name, String course_describe) {
        this.course_name = course_name;
        this.course_describe = course_describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_describe() {
        return course_describe;
    }

    public void setCourse_describe(String course_describe) {
        this.course_describe = course_describe;
    }
}
