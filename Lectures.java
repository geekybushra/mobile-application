package com.example.logic.learningeng;

import android.app.Activity;
import android.os.Bundle;

public class Lectures  {int id;
    String lecture_name;
    String lecture_describe;
    String lecture_url;
    String lecture_level;
    String course_name;

    public Lectures() {
    }

    public Lectures(String lecture_name, String lecture_describe, String lecture_url, String lecture_level, String course_name) {
        this.lecture_name = lecture_name;
        this.lecture_describe = lecture_describe;
        this.lecture_url = lecture_url;
        this.lecture_level = lecture_level;
        this.course_name = course_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecture_name() {
        return lecture_name;
    }

    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }

    public String getLecture_describe() {
        return lecture_describe;
    }

    public void setLecture_describe(String lecture_describe) {
        this.lecture_describe = lecture_describe;
    }

    public String getLecture_url() {
        return lecture_url;
    }

    public void setLecture_url(String lecture_url) {
        this.lecture_url = lecture_url;
    }

    public String getLecture_level() {
        return lecture_level;
    }

    public void setLecture_level(String lecture_level) {
        this.lecture_level = lecture_level;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
