package com.company;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("/Users/alexandrelemos/Downloads/normal/A1room.txt");
        File file1 = new File("/Users/alexandrelemos/Downloads/normal/Aroom.txt");
        File file2 = new File("/Users/alexandrelemos/Downloads/normal/Broom.txt");

        File file3 = new File("/Users/alexandrelemos/Downloads/normal/lessonsTagusC.txt");
        File file4 = new File("/Users/alexandrelemos/Downloads/normal/lessonsTagusB.txt");
        File file5 = new File("/Users/alexandrelemos/Downloads/normal/lessonsTagusA.txt");

        List<Room> af = Load.readROOM(file);
        List<Room> afb = Load.readROOM(file1);
        List<Room> rp = Load.readROOM(file2);
        List<Lesson> ll = Load.readLesson(file3);
        List<Lesson> llb = Load.readLesson(file4);
        List<Lesson> ls = Load.readLesson(file5);
        System.out.printf("RUN");
        run(af, afb,ll, llb,rp, ls);
        System.out.println("Seat A:"+spaceFails(af));
        System.out.println("Seat B:"+spaceFails(afb));
        System.out.println("Seat C:"+spaceFails(rp));
        System.out.println("Seat A:"+spaceFull(af));
        System.out.println("Seat B:"+spaceFull(afb));
        System.out.println("Seat C:"+spaceFull(rp));

       // stats(rp);
       // stats(af);
       // stats(afb);
        af = Load.readROOM(file);
        afb = Load.readROOM(file1);
        rp = Load.readROOM(file2);
        ll = Load.readLesson(file3);
        llb = Load.readLesson(file4);
        ls = Load.readLesson(file5);
        System.out.printf("RUN1");

        run1(af, afb,ll, llb,rp, ls);
        System.out.println("Seat A:"+spaceFails(af));
        System.out.println("Seat B:"+spaceFails(afb));
        System.out.println("Seat C:"+spaceFails(rp));
        System.out.println("Seat A:"+spaceFull(af));
        System.out.println("Seat B:"+spaceFull(afb));
        System.out.println("Seat C:"+spaceFull(rp));
       // stats(rp);
       // stats(af);
       // stats(afb);

        af = Load.readROOM(file);
        af.addAll(Load.readROOM(file1));
        af.addAll(Load.readROOM(file2));

        //afb = Load.readROOM(file1);
        //rp = Load.readROOM(file2);
        ll = Load.readLesson(file3);
        ll.addAll(Load.readLesson(file4));
        ll.addAll(Load.readLesson(file5));

        // llb = Load.readLesson(file4);
        //ls = Load.readLesson(file5);
        System.out.printf("RUN2");

        run2(af, afb,ll, llb,rp, ls);
        /*System.out.println("Seat A:"+spaceFails(af));
        System.out.println("Seat B:"+spaceFails(afb));
        System.out.println("Seat C:"+spaceFails(rp));
        System.out.println("Seat A:"+spaceFull(af));
        System.out.println("Seat B:"+spaceFull(afb));
        System.out.println("Seat C:"+spaceFull(rp));*/
       // stats(rp);
       // stats(af);
       // stats(afb);



    }

    private static void stats(List<Room> room) {
        double mean = 0;
        int trans = 0;
        int wtrans = 0;
        for (int i = 0; i < room.size(); i++) {
            mean += room.get(i).Vacancies();
            trans += room.get(i).numberTransistion();
            wtrans += room.get(i).weightedNumberTransistion();
            //System.out.println(room.get(i).getName() + "," + room.get(i).percentegesVacancies());
        }
        mean = mean / room.size();
        System.out.println("MEAN:"+mean);
        System.out.println("TRANS:"+trans);
        System.out.println("WTRANS"+wtrans);
        double variance = 0;
        for (int i = 0; i < room.size(); i++) {
            variance += (room.get(i).Vacancies() - mean) * (room.get(i).Vacancies() - mean);
        }
        System.out.println("V"+variance);
    }

    private static void run(List<Room> af, List<Room> afb,List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
        Collections.sort(ll, new CompareByStart());
        Collections.sort(llb, new CompareByStart());
        Collections.sort(ls, new CompareByStart());
       /* Collections.sort(ll, new CompareByEnd());
        Collections.sort(llb, new CompareByEnd());
        Collections.sort(ls, new CompareByEnd());*/

        Collections.sort(af, new CompareBySize());
        Collections.sort(afb, new CompareBySize());
        Collections.sort(rp, new CompareBySize());
        System.out.println("Sorted run");
        assign(af, ll);
        assign(afb, llb);
        assign(rp, ls);


    }

    private static void run2(List<Room> af, List<Room> afb,List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
        //Collections.sort(af, new CompareBySize());
        //Collections.sort(afb, new CompareBySize());
        //Collections.sort(rp, new CompareBySize());
        System.out.println("Sorted run2");

        assign2(af, ll);
        //assign2(rp, ls);
        //assign2(afb, llb);



    }


    private static void assign2(List<Room> af, List<Lesson> ll) {
        int alloc = 0;
        boolean alloced[] = new boolean[ll.size()];
        Arrays.fill(alloced, false);

        double benifit=0;
        while (ll.size() > alloc) {
            int le = 0;
            int ln=0;
            Room rF = null;
            Lesson lF = null;
            double d = Double.MAX_VALUE;
            for (Lesson l : ll
                    ) {

                if (!alloced[le]) {

                    for (Room r :
                            af) {
                        double d1 = r.newCost(l);
                        if (d1 < d && d1>=0) {
                            d = d1;
                            lF = l;
                            rF = r;
                            ln=le;
                        }
                        if(d1 == d && d1>=0) {
                            if(r.benifit(l)>rF.benifit(lF)){
                                d = d1;
                                lF = l;
                                rF = r;
                                ln=le;
                            }
                        }


                    }


                }


                le++;

            }
            if(rF==null || lF==null)
                System.out.println("FAIL");
            else if (rF.assing(lF)) {
                alloced[ln] = true;
                benifit+=rF.benifit(lF);

            }else {
                System.exit(1);
            }
            alloc++;


        }

        System.out.println(alloc);
        System.out.println(benifit);
    }
    private static void assign1(List<Room> af, List<Lesson> ll) {
        int alloc = 0;
        boolean alloced[] = new boolean[ll.size()];
        Arrays.fill(alloced, false);
        Room rF = null;
        Lesson lF = null;

        while (ll.size() > alloc) {
            int le = 0;
            int ln=0;
            double d = -Double.MAX_VALUE;
            for (Lesson l : ll
                    ) {
                if (!alloced[le]) {

                    for (Room r :
                            af) {
                        double d1 = r.cost(l);
                        if (d1 >= d) {
                            d = d1;
                            lF = l;
                            rF = r;
                            ln=le;
                        }


                    }
                }
                le++;

            }
            if (rF.assing(lF)) {
                alloced[ln] = true;
                alloc++;
            }

        }
    }


    private static void run1(List<Room> af, List<Room> afb,List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
        Collections.sort(ll, new CompareByDuration());
        Collections.sort(llb, new CompareByDuration());
        Collections.sort(ls, new CompareByDuration());

        Collections.sort(af, new CompareBySize());
        Collections.sort(afb, new CompareBySize());
        Collections.sort(rp, new CompareBySize());
        System.out.println("Sorted run1");

        assign(af, ll);
        assign(afb, llb);
        assign(rp, ls);


    }

    private static void assign(List<Room> af, List<Lesson> ll) {
        int alloc = 0;
        while (ll.size() > alloc) {
            Lesson l = ll.get(alloc);
            boolean assing=false;
            for (Room r :
                    af) {
                if (r.assing(l)) {
                    alloc++;
                    assing=true;
                    break;
                }
            }
            if(!assing){
                System.out.println("FAIL");
                return;
            }


        }
    }

    public static int spaceFails(List<Room> room){
        int result=0;
        for (int i = 0; i < room.size(); i++) {
            result+=room.get(i).spaceFails();
        }
        return result;
    }

    public static int spaceFull(List<Room> room){
        int result=0;
        for (int i = 0; i < room.size(); i++) {
            result+=room.get(i).stuff();
        }
        return result;
    }


}
