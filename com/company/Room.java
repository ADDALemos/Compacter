package com.company;

/**
 * Created by Alexandre on 10/06/2017.
 */
public class Room {
    public static final int DAYS = 5;
    public static final int SLOTS = 26;
    public static final int COST = 1000000;
    String id;
    private int capacity;
    private int location;
    private String name;
    private Lesson[][] allocaton;

    public Room(int capacity, int location, String name, String id) {
        this.capacity = capacity;
        this.location = location;
        allocaton = new Lesson[5][26];
        this.name = name;
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean assing(Lesson l){
        if (getCapacity() < Math.floor(l.getStudents() - (l.getStudents() * l.getAlfa())))
            return false;
        for (int i = l.getStart(); i < l.getEnd(); i++) {
            if(allocaton[l.getDay()][i]!=null){
                return false;
            }
        }
        for (int i = l.getStart(); i < l.getEnd(); i++) {
            allocaton[l.getDay()][i]=l;
        }
        if(this.getCapacity()<l.getStudents()){
           // System.out.println("SHOULD be false"+this.name+" "+l.getStudents());
            //System.out.println(this.name);
            return true;
        }
        else
            return true;
    }
    public double percentegesVacancies(){
        int counterV = 0;
        int counterT = 0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                if(allocaton[i][j]==null)
                    counterV++;

                counterT++;
            }

        }
        return (counterV*100.0f/counterT);
    }
    public int Vacancies(){
        int counter = 0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                if(allocaton[i][j]==null)
                    counter++;
            }

        }
        return counter;
    }
    public  int stuff(){
        int counter=0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                if (allocaton[i][j] != null) {
                    if (j != 0) {
                        if (allocaton[i][j - 1] != allocaton[i][j])
                            counter++;

                    } else
                        counter++;
                }
            }

        }
        return counter;
    }
    public void printLessonsBadAlloc(){
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                // System.out.println(allocaton[i][j-1]+" "+allocaton[i][j-1]);
                if (allocaton[i][j] != null)
                    System.out.print( (allocaton[i][j].getStudents() > capacity ?
                            allocaton[i][j].getName()+";Roomname;"+getName()+";Std;"+ allocaton[i][j].getStudents()
                                    +";cap;"+getCapacity()+"\n" : ""));

            }
        }

    }

    public int roomoccupation() {
        int res = 0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                // System.out.println(allocaton[i][j-1]+" "+allocaton[i][j-1]);
                if (allocaton[i][j] != null)
                    res += allocaton[i][j].getStudents();


            }
        }
        return res;

    }

    public int roomoccupation1() {
        int res = 0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                // System.out.println(allocaton[i][j-1]+" "+allocaton[i][j-1]);
                if (allocaton[i][j] != null)
                    res += getCapacity();


            }
        }
        return res;

    }

    public int numberStudent() {
        int counter = 0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                // System.out.println(allocaton[i][j-1]+" "+allocaton[i][j-1]);
                if (allocaton[i][j] != null)
                    counter += (allocaton[i][j].getStudents() > capacity ?
                            allocaton[i][j].getStudents() - capacity : 0);

            }
        }
        return counter;

    }

    public  int numberTransistion(){
        return numberTransistion(-1, -1, -1, false);

    }

    private int numberTransistion(int day, int slotmin, int slotmax, Boolean clear) {
        int counter=0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 1; j < SLOTS; j++) {
                int temp1=0;
                // System.out.println(allocaton[i][j-1]+" "+allocaton[i][j-1]);
                if (day == i && (j - 1) >= slotmin && (j - 1) < slotmax && !clear)
                    temp1 += 1;
                else if (day == i && (j - 1) >= slotmin && (j - 1) < slotmax && clear)
                    temp1 += 0;
                else if (allocaton[i][j - 1] == null)
                    temp1+=0;
                else
                    temp1+=1;
                if (day == i && (j - 1) >= slotmin && (j - 1) < slotmax && !clear)
                    temp1 += 1;
                else if (day == i && (j - 1) >= slotmin && (j - 1) < slotmax && clear)
                    temp1 += 1;
                else if (allocaton[i][j] == null)
                    temp1+=0;
                else
                    temp1+=1;
                if(temp1==1)
                    counter++;

            }
        }
        return counter;
    }
    public  double weightedNumberTransistion(){
        double counter=0;
        int vancacies=0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                if(allocaton[i][j]==null)
                    vancacies++;
                else
                    vancacies=0;
                if(j!=0)
                if(allocaton[i][j]!=allocaton[i][j-1]&&allocaton[i][j-1]!=null&&allocaton[i][j]==null) {
                    counter += 1 / vancacies;
                }
            }
        }
        return counter;
    }
    public double benifit(Lesson l){

        if(l.getStudents()<this.getCapacity())
            return l.getStudents();
        else
            return this.getCapacity();

    }
    public double newCost(Lesson l){
        if (getCapacity() < Math.floor(l.getStudents() - (l.getStudents() * l.getAlfa())))
            return -COST;
        for (int i = l.getStart(); i < l.getEnd(); i++) {
            if(allocaton[l.getDay()][i]!=null){
                return -COST;
            }
        }
        if(l.getStudents()>this.getCapacity())
            return l.getStudents()-this.getCapacity();
        else
            return 0;
    }
    public double cost(Lesson l) {
        double result=0;
        for (int i = l.getStart(); i < l.getEnd(); i++) {
            if(allocaton[l.getDay()][i]!=null){
                result=-100;
            }
        }
        if(result==0)
            result++;
        result+=Math.abs(l.getStudents()-this.getCapacity())*-1;
        try {
            if(allocaton[l.getDay()][l.getStart()-1]!=null&&allocaton[l.getDay()][1-l.getStart()+l.getLenght()]!=null)
                result-=1;
        } catch (Exception e) {
         //   e.printStackTrace();
        }


        return result;
    }

    public int spaceFails() {
        int result =0;
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < SLOTS; j++) {
                if(allocaton[i][j]!=null){
                    Lesson l =allocaton[i][j];

                    if(this.getCapacity()<allocaton[i][j].getStudents()){
                        if (j == 0)
                            result += Math.abs(this.getCapacity() - allocaton[i][j].getStudents());
                        else if (l != allocaton[i][j - 1])
                            result += Math.abs(this.getCapacity() - allocaton[i][j].getStudents());
                    }

                }

            }
        }
        return result;

    }

    public boolean alocable(Lesson l) {
        for (int i = l.getStart(); i < l.getLenght(); i++) {
            if(allocaton[l.getDay()][i]!=null){
                return false;
            }
        }
        return true;
    }

    public int next(Lesson l) {
        int compact = 0;
        if (l.getStart() - 1 < 0)
            compact++;
        else if (allocaton[l.getDay()][l.getStart() - 1] != null)
            compact++;
        if (l.getEnd() >= SLOTS)
            compact++;
        else if (allocaton[l.getDay()][l.getEnd()] != null)
            compact++;
        return compact;


    }

    public Lesson getLesson(int k, int l) {
        return allocaton[k][l];
    }

    public boolean free(int day, int timeslot, int lenght) {
        for (int i = 0; i < lenght; i++) {
            if (allocaton[day][timeslot + i] != null)
                return false;
        }
        return true;
    }

    public boolean sameSize(int day, int slot, int lenght) {
        Lesson l = null;
        for (int i = 0; i < lenght; i++) {
            if (allocaton[day][slot + i] != null) {
                if (i != 0) {
                    if (l != allocaton[day][slot + i])
                        return false;
                } else {
                    l = allocaton[day][slot + i];
                    if (l.getLenght() != lenght)
                        return false;
                }

            } else
                return false;
        }
        return true;
    }

    public int tryswamp(Lesson lessonNew, Lesson lessonOld) {
        if (lessonNew != null)
            return numberTransistion(lessonNew.getDay(), lessonNew.getStart(), lessonNew.getStart() + lessonNew.getLenght(), false) - numberTransistion();
        else
            return numberTransistion(lessonOld.getDay(), lessonOld.getStart(), lessonOld.getStart() + lessonOld.getLenght(), true) - numberTransistion();

    }

    public Lesson swamp(Lesson lesson) {
        if (lesson != null) {
            Lesson old = allocaton[lesson.getDay()][lesson.getStart()];
            for (int i = 0; i < lesson.getLenght(); i++) {
                allocaton[lesson.getDay()][lesson.getStart() + i] = lesson;
            }
            return old;
        }
        return null;
    }
}
