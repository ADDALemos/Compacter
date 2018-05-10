package com.company;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        File file = new File("C:\\Users\\alexa\\Desktop\\normal\\EnsinoL2448131360898.txt");//EnsinoL2448131360898
        File file1 = new File("C:\\Users\\alexa\\Desktop\\normal\\EnsinoM2448131360898.txt");
        File file2 = new File("C:\\Users\\alexa\\Desktop\\normal\\EnsinoSmall2448131360898.txt");

        File fileS = new File("/Volumes/MAC/normal/Alameda17Seg.txt");
        File fileT = new File("/Volumes/MAC/normal/Alameda16Ter.txt");
        File fileQ2 = new File("/Volumes/MAC/normal/Alameda16Qui.txt");
        File fileQ1 = new File("C:\\Users\\alexa\\Desktop\\normal\\Taguspark16.txt");
        File fileS1 = new File("/Volumes/MAC/normal/Alameda16Seg.txt");
        File file4 = new File("/Volumes/MAC/normal/Taguspark16.txt");
        File file5 = new File("/Volumes/MAC/normal/Taguspark17.txt");

        List<Room> af = Load.readROOM(file);

        af.addAll(Load.readROOM(file1));
        af.addAll(Load.readROOM(file2));
        //  af=Load.randomCloseRoom(0.1, af);
        List<com.company.Lesson> ll = com.company.Load.readLesson(fileQ1);
     /*   ll.addAll(Load.readLesson(fileT));
        ll.addAll(Load.readLesson(fileQ1));
        ll.addAll(Load.readLesson(fileQ2));
        ll.addAll(Load.readLesson(fileS1));*/
        //com.company.Load.print(ll,af);
        //System.out.println("l" + ll.size());
         //  Load.allocROOMfromLesson(ll,af);
        //int c = 0;
        for (Room r :
                af) {
            r.printLessonsBadAlloc();
        }
        //System.out.println(c);
        // stats(af);
//        assign2(af, ll);
        ILPrun.run(af.size(), ll.size(), ll, af);
        System.out.println("a");
        for (Room r :
                af) {
            r.printLessonsBadAlloc();
        }
        System.exit(0);

        int s = 0;
        for (Lesson l :
                ll) {
            s += l.getStudents() * l.getLenght();

        }
        System.out.println(s);
        System.exit(0);
/*
        //List<Lesson> llb = Load.readLesson(file4);
        //List<Lesson> ls = Load.readLesson(file5);
        int v=0;
        for (Room r:
            af ) {
            v=26*5;

        }
        System.out.println(v);
        int v1=0;
        for (Lesson l:
                ll ) {
            v1+=l.getLenght();
        }

        System.out.println(v1);
        System.out.println(v1/v);
        System.exit(0);

       /* List<Room> af = Load.readROOM(file);
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

        stats(rp);
        stats(af);
        stats(afb);

        af = Load.readROOM(file);
        afb = Load.readROOM(file1);
        //af.addAll(Load.readROOM(file1));
        //af.addAll(Load.readROOM(file2));
        rp = Load.readROOM(file2);
        ll = Load.readLesson(file3);
        //ll.addAll(Load.readLesson(file4));
        llb = Load.readLesson(file4);
        //ll.addAll(Load.readLesson(file5));
        ls = Load.readLesson(file5);

        System.out.printf("RUN1");

        run1(af, afb,ll, llb,rp, ls);
        System.out.println("Seat A:"+spaceFails(af));
        System.out.println("Seat B:"+spaceFails(afb));
        System.out.println("Seat C:"+spaceFails(rp));
        System.out.println("Seat A:"+spaceFull(af));
        System.out.println("Seat B:"+spaceFull(afb));
        System.out.println("Seat C:"+spaceFull(rp));
        stats(rp);
        stats(af);
        stats(afb);

        af = Load.readROOM(file1);
        af.addAll(Load.readROOM(file2));
        af.addAll(Load.readROOM(file));

       // afb = Load.readROOM(file1);
        //rp = Load.readROOM(file2);
        ll = Load.readLesson(file4);
        ll.addAll(Load.readLesson(file4));
        ll.addAll(Load.readLesson(file3));

        //llb = Load.readLesson(file4);
        //ls = Load.readLesson(file5);
        System.out.printf("RUN2");

        run2(af, afb,ll, llb,rp, ls);
        System.out.println("Seat A:"+spaceFails(af));
        System.out.println("Seat B:"+spaceFails(afb));
        System.out.println("Seat C:"+spaceFails(rp));
        System.out.println("Seat A:"+spaceFull(af));
        System.out.println("Seat B:"+spaceFull(afb));
        System.out.println("Seat C:"+spaceFull(rp));
       // stats(rp);
       stats(af);
       // stats(afb);*/
        return;



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
       /* Collections.sort(ll, new CompareByStart());
        Collections.sort(llb, new CompareByStart());
        Collections.sort(ls, new CompareByStart());*/
        Collections.sort(ll, new CompareByEnd());
        Collections.sort(llb, new CompareByEnd());
        Collections.sort(ls, new CompareByEnd());

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
        System.out.println("room");
        //assign2(rp, ls);
        System.out.println("b");
        //assign2(afb, llb);



    }


    private static void assign2(List<Room> af, List<Lesson> ll) {
        int alloc = 0;
        int comp = 0;
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
                            } else if(r.benifit(l)==rF.benifit(lF)&&r.next(l)>r.next(lF)){
                                d = d1;
                                lF = l;
                                rF = r;
                                ln=le;
                                comp++;
                            }
                        }


                    }


                }


                le++;

            }
            if(rF==null || lF==null)
                System.out.println("FAIL1");
            else if (rF.assing(lF)) {
                alloced[ln] = true;
                benifit += rF.benifit(lF) * lF.getLenght();

            }else {
                System.exit(1);
            }
            alloc++;


        }
        int i=0;
        for (Lesson l:
             ll) {
            if (!alloced[i])
                System.out.println("A" + l.getStudents());
                i++;

        }

        System.out.println(alloc);
        System.out.println(benifit);
        int v = 0;
        for (Room r :
                af) {
            v += r.numberTransistion();
        }
        System.out.println("W" + v);
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
            } else
                alloc++;


        }
        int i=0;
        for (Lesson l:
                ll) {
            if (!alloced[i])
                System.out.println(l.getStudents());
            i++;

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
        System.out.println("s");
        assign(afb, llb);
        System.out.println("n");
        assign(rp, ls);


    }

    private static void assign(List<Room> af, List<Lesson> ll) {
        int alloc = 0;
        int s=0;
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
                alloc++;
                s+=l.getStudents();
                //return;
            }


        }
        System.out.println(s);
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
