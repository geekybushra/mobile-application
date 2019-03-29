package com.example.logic.learningeng;


public class Activities {
    int id;
    int studentId;
    int testId;
    int score;

    public Activities() {
    }

    public Activities(int studentId, int testId, int score) {
        this.studentId = studentId;
        this.testId = testId;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
