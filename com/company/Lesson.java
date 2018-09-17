package com.company;

/**
 * Created by Alexandre on 10/06/2017.
 */
public class Lesson {
    private static int counter = 0;
    public int teachers;
    String name;
    String originalRoom = null;
    String idDegree;
    private int end;
    private int id;
    private int start;
    private int lenght;
    private int students;
    private int day;
    private double alfa = 1;

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

    public Lesson(int start, int end, String token, int students, int day, double v) {
        this(start, end, token, students, day);
        alfa = v;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (getEnd() != lesson.getEnd()) return false;
        if (getStart() != lesson.getStart()) return false;
        if (getLenght() != lesson.getLenght()) return false;
        if (getDay() != lesson.getDay()) return false;
        return getName() != null ? getName().equals(lesson.getName()) : lesson.getName() == null;
    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName().hashCode();
        result = 31 * result + originalRoom.hashCode();
        result = 31 * result + getEnd();
        result = 31 * result + getId();
        result = 31 * result + getStart();
        result = 31 * result + getLenght();
        result = 31 * result + getStudents();
        result = 31 * result + getDay();
        temp = Double.doubleToLongBits(getAlfa());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", originalRoom='" + originalRoom + '\'' +
                ", end=" + end +
                ", id=" + id +
                ", start=" + start +
                ", lenght=" + lenght +
                ", students=" + students +
                ", day=" + day +
                '}';
    }

    public void decrement() {
        counter--;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public boolean differSize(Lesson l) {
        return l.getStudents() != this.getStudents();

    }

    public boolean differRoom(Lesson l) {
        return originalRoom != l.originalRoom;
    }

    public boolean differSlot(Lesson l) {
        if (l.getDay() != getDay())
            return true;
        return l.getStart() != getStart();
    }

    public boolean differDuration(Lesson l) {
        return l.getLenght() != getLenght();
    }
}
