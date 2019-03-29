package com.example.logic.learningeng;

import android.app.Activity;
import android.os.Bundle;

public class Enrolls {
    int id;
    int StudentId;
    int CourseId;
    String level;
    int scoreSum;

    public Enrolls() {
    }

    public Enrolls(int studentId, int courseId, String level) {
        StudentId = studentId;
        CourseId = courseId;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(int scoreSum) {
        this.scoreSum = scoreSum;
    }
}
