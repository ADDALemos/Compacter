package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexandre on 14/06/2017.
 */
public class Load {
    private static double slack = .3;

    Load() {


    }

    public static List<Room> readROOM(File file) {
        return readROOM(file, 0);
    }

    public static List<Room> readROOM(File file, int ignore) {
        Scanner scanner = null;
        List<Room> r = new ArrayList<Room>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(file);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] tokens = scanner.nextLine().split(";");
            r.add(new Room(Integer.parseInt(tokens[1]), 0, tokens[0], tokens[2]));//0-> no segundo argumento no ficheiros mais recentes
        }
        return r;
    }

    public static List<Room> randomCloseRoom(double ignore, List<Room> r) {
        List<Room> ign = new ArrayList<>();
        int originalSize = r.size();
        for (; ign.size() < Math.ceil(ignore * originalSize); ) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, r.size());
            ign.add(r.get(randomNum));
            r.remove(randomNum);
            System.out.println(randomNum + " " + r.get(randomNum).getName());

        }
        return r;
    }


    public static List<Lesson> readLesson(File file) {
        int slot[][] = new int[16][2];
        int slotV = 0;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 2; j++) {
                slot[i][j] = slotV;
                slotV++;
            }
        }
        Scanner scanner = null;
        List<Lesson> r = new ArrayList<Lesson>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            try {

                String[] tokens = s.split(";");
                int startHalf = 1;
                if (Integer.parseInt(tokens[2]) == 0)
                    startHalf = 0;
                int endHafl = 1;
                if (Integer.parseInt(tokens[4]) == 0)
                    endHafl = 0;
                if (Integer.parseInt(tokens[6]) <= 6) {
                    //System.out.println(slot[Integer.parseInt(tokens[1])-8][startHalf]+" "+slot[Integer.parseInt(tokens[3])-8][endHafl]+" "+tokens[0]+" "+Integer.parseInt(tokens[5])+" "+Integer.parseInt(tokens[6]));
                    if (tokens.length > 7) {
                        Lesson nl = new Lesson(slot[Integer.parseInt(tokens[1]) - 8][startHalf], slot[Integer.parseInt(tokens[3]) - 8][endHafl], tokens[0], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]) - 2, tokens[7]);
                        nl.setAlfa(slack);
                        boolean test = true;
                        for (Lesson l :
                                r) {
                            if (l.equals(nl)) {
                                test = false;
                                nl.decrement();
                                break;
                            }

                        }
                        if (test)
                            r.add(nl);
                    } else
                        r.add(new Lesson(slot[Integer.parseInt(tokens[1]) - 8][startHalf], slot[Integer.parseInt(tokens[3]) - 8][endHafl], tokens[0], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]) - 2, slack));

                } else
                    System.out.println(s);

                //acronym_1;hourS;minS;hourE;minE;currentC;day
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(s);
            }
        }
        return r;
    }

    public static void print(List<Lesson> l, List<Room> r) {
        List<Lesson> l0 = new ArrayList<com.company.Lesson>(), l1 = new ArrayList<com.company.Lesson>(), l2 = new ArrayList<com.company.Lesson>(), l3 = new ArrayList<com.company.Lesson>(), l4 = new ArrayList<com.company.Lesson>();
        for (Lesson le :
                l) {
            if (le.getDay() == 0) {
                //System.out.println("0");
                l0.add(le);
            } else if (le.getDay() == 0) {
                //  System.out.println("1");
                l1.add(le);
            } else if (le.getDay() == 0) {
                //System.out.println("2");
                l2.add(le);
            } else if (le.getDay() == 0) {
                //System.out.println("3");
                l3.add(le);
            }
            if (le.getDay() == 0) {
                //System.out.println("4");
                l4.add(le);
            }


        }
        int slot = 0, geral = 0;
        for (Room r1 :
                r) {

            int count = 0;
            // System.out.println(" S T Q Q S");
            for (com.company.Lesson l01 :
                    l) {
                if (r1.id.equals(l01.originalRoom)) {
                    count += l01.getLenght();
                    System.out.println(l01.getStart() + " " + l01.getDay() + " " + l01.getLenght() + " " + l01.name);
                }

                //System.out.print(l0.getName()+" ");
                //  else
                // System.out.print("  ");
            }


            if (count != 0) {
                //System.out.println(count);
                //  if(r1.getName().charAt(0)!='L'){
                geral += count;

                slot += 26 * 5;
                System.out.println(r1.getName() + " " + ((double) count / (26 * 5)) * 100);
            }

            // }


        }
        for (com.company.Lesson l01 :
                l) {
            if (l01.originalRoom == null) {
                // geral += l01.getLenght();
                //  System.out.println(l01.getLenght()+" "+l01.name);

            }

            //System.out.print(l0.getName()+" ");
            //  else
            // System.out.print("  ");
        }
        System.out.println(((double) geral / slot) * 100);
    }

    public static void allocROOMfromLesson(List<Lesson> l, List<Room> r) {
        for (com.company.Lesson l01 :
                l) {
            boolean b = false;
            for (int i = 0; i < r.size(); i++) {
                if (r.get(i).id.equals(l01.originalRoom)) {
                    r.get(i).assing(l01);
                    b = true;
                }
            }
            System.out.print((b != true ? l01.originalRoom + "\n" : ""));

        }
    }

    public static List<Room> loadROOMA() {
        List<Room> r = new ArrayList<Room>();
        for (int i = 0; i < 5; i++) {
            r.add(new Room(100, 0, " " + i, " " + i));
        }
        return r;
    }

    public static List<Room> loadROOMB() {
        List<Room> r = new ArrayList<Room>();
        for (int i = 0; i < 5; i++) {
            r.add(new Room(25, 0, " " + i, " " + i));
        }
        return r;
    }

    public static List<Lesson> loadLessonA() {
        List<Lesson> r = new ArrayList<Lesson>();
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
            r.add(new Lesson(start, start + end, i + " ", 25, day));
        }
        return r;
    }

}

