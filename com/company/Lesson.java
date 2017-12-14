package com.company;

/**
 * Created by Alexandre on 10/06/2017.
 */
public class Lesson {
    private static int counter = 0;
    String name;
    String originalRoom = null;
    private int end;
    private int id;
    private int start;
    private int lenght;
    private int students;
    private int day;

    public Lesson(int start, int end, String name, int students, int day) {
        this.start = start;
        this.end = end;
        this.lenght = end - start;
        this.name = name;
        this.students = students;
        this.day = day;
        this.id = counter++;

    }

    public Lesson(int start, int end, String name, int students, int day, String r) {
        this.start = start;
        this.end = end;
        this.lenght = end - start;
        this.name = name;
        this.students = students;
        this.day = day;
        this.originalRoom = r;
        this.id = counter++;

    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (getEnd() != lesson.getEnd()) return false;
        return getStart() == lesson.getStart();
    }

    @Override
    public int hashCode() {
        int result = getEnd();
        result = 31 * result + getStart();
        return result;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLenght() {
        if (lenght > 0)
            return lenght;
        return 0;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public int getId() {
        return id;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
