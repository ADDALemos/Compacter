package com.company;

/**
 * Created by Alexandre on 10/06/2017.
 */
public class Lesson {
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int end;
    private int start;
    private int lenght;
    private String name;
    private int students;
    private int day;


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public Lesson(int start, int end, String name, int students, int day) {
        this.start = start;
        this.end=end;
        this.lenght = end-start;
        this.name = name;
        this.students = students;
        this.day=day;
    }

    public int getDay() {
        return day;
    }
}
