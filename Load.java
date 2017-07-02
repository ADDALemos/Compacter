package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Alexandre on 14/06/2017.
 */
public class Load {

    Load(){


    }
    public static List<Room> readROOM(File file){
        Scanner scanner = null;
        List<Room> r= new ArrayList<Room>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        while(scanner.hasNext()){
            String[] tokens = scanner.nextLine().split(";");
            r.add(new Room(Integer.parseInt(tokens[1]),0,tokens[0]));
        }
        return r;
    }


    public static List<Lesson> readLesson(File file){
        int slot[][]=new int[13][2];
        int slotV=0;
        for (int i=0;i<13;i++){
            for (int j = 0; j < 2; j++) {
                slot[i][j]=slotV;
                slotV++;
            }}
        Scanner scanner = null;
        List<Lesson> r= new ArrayList<Lesson>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        while(scanner.hasNext()){
            String[] tokens = scanner.nextLine().split(";");
            int startHalf=1;
            if(Integer.parseInt(tokens[2])==0)
                startHalf=0;
            int endHafl=1;
            if(Integer.parseInt(tokens[4])==0)
                endHafl=0;
           // System.out.println(slot[Integer.parseInt(tokens[1])-8][startHalf]+" "+slot[Integer.parseInt(tokens[3])-8][endHafl]+" "+tokens[0]+" "+Integer.parseInt(tokens[5])+" "+Integer.parseInt(tokens[6]));
            r.add(new Lesson(slot[Integer.parseInt(tokens[1])-8][startHalf],slot[Integer.parseInt(tokens[3])-8][endHafl],tokens[0],Integer.parseInt(tokens[5]),Integer.parseInt(tokens[6])-2));
            //acronym_1;hourS;minS;hourE;minE;currentC;day
        }
        return r;
    }


    public static List<Room> loadROOMA(){
        List<Room> r =new ArrayList<Room>();
        for (int i = 0; i < 5; i++) {
            r.add(new Room(100,0," "+i));
        }
        return  r;
    }
    public static List<Room> loadROOMB(){
        List<Room> r =new ArrayList<Room>();
        for (int i = 0; i < 5; i++) {
            r.add(new Room(25,0," "+i));
        }
        return  r;
    }
    public static List<Lesson> loadLessonA(){
        List<Lesson> r =new ArrayList<Lesson>();
        for (int i = 0; i < 5; i++) {
            final int start =
                    (new Random().nextInt(21)
                    );
            final int end =
                    (new Random().nextInt(3)
                    );
            final int day =
                    (new Random().nextInt(5)
                    );
            r.add(new Lesson( start,start+end,i+" ",25,day));
        }
        return  r;
    }

}

