package com.company;

import com.sun.tools.javac.util.Pair;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File filea = new File("/Users/alexandrelemos/Downloads/normal/lessonsTagus1Semestre20162017.txt");
        File fileb = new File("/Users/alexandrelemos/Downloads/normal/lessonsTagus1Semestre20172018.txt");
        // Load.compareLesson(fileb,filea);
        //System.exit(0);

        File file = new File("/Volumes/MAC/normal/EnsinoL2448131360897.txt");//EnsinoL2448131360898
        File file1 = new File("/Volumes/MAC/normal/EnsinoM2448131360897.txt");
        File file2 = new File("/Volumes/MAC/normal/EnsinoSmall2448131360897.txt");

        File fileS = new File("/Volumes/MAC/normal/Alameda17Seg.txt");
        File fileT = new File("/Volumes/MAC/normal/Alameda17Ter.txt");
        File fileQ2 = new File("/Volumes/MAC/normal/Alameda17Qui.txt");
        File fileQ1 = new File("/Volumes/MAC/normal/Alameda17Qua.txt");
        File fileS1 = new File("/Volumes/MAC/normal/Alameda17Sex.txt");
        //  File file4 = new File("/Volumes/MAC/normal/Taguspark17.txt");
        // File file5 = new File("/Volumes/MAC/normal/Taguspark16.txt");


        List<Room> af = Load.readROOM(file);

        af.addAll(Load.readROOM(file1));
        af.addAll(Load.readROOM(file2));


        //  af=Load.randomCloseRoom(0.1, af);
        List<com.company.Lesson> ll = com.company.Load.readLesson(fileS);
        //     ll.addAll(Load.readLesson(file5));
        /*ll.addAll(Load.readLesson(fileQ1));
        ll.addAll(Load.readLesson(fileQ2));
        ll.addAll(Load.readLesson(fileS1));*/
        GRASP(3, af, ll, (int) (ll.size() * .6));// .6 1000 0.1 10

        int st = 0;
        for (Lesson l :
                ll) {
            st += l.getStudents();
        }
        System.out.println(st);
        System.exit(1);

      /*  ll.addAll(Load.readLesson(fileT));
        ll.addAll(Load.readLesson(fileQ1));
        ll.addAll(Load.readLesson(fileQ2));
        ll.addAll(Load.readLesson(fileS1));*/

/****** Load Hand-made solution
 Load.allocROOMfromLesson(ll,af);
 int s = 0,v=0;
 for (Room l :
 af) {
 v+=l.roomoccupation();
 if(l.roomoccupation()>0)
 s+=l.roomoccupation1();
 }
 System.out.println(s);
 System.out.println(v); System.exit(1);
 /*******Total number of students
 int s = 0;
 for (Lesson l :
 ll) {
 s += l.getStudents() * l.getLenght();

 }
 System.out.println(s);*/
/*******Print stats
 stats(af);*/
        // assign2(af, ll);
/******Overbooked*/
        System.out.println("Overbooked:");
        for (Room r :
                af) {
            r.printLessonsBadAlloc();
        }

/*******RUN ILP*/
        ILPrun.run(af.size(), ll.size(), ll, af);


/******* Greedy Algorithm Comparison and Orderings

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
        System.out.println("MEAN:" + mean);
        System.out.println("TRANS:" + trans);
        System.out.println("WTRANS" + wtrans);
        double variance = 0;
        for (int i = 0; i < room.size(); i++) {
            variance += (room.get(i).Vacancies() - mean) * (room.get(i).Vacancies() - mean);
        }
        System.out.println("V" + variance);
    }

    private static void run(List<Room> af, List<Room> afb, List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
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

    private static void run2(List<Room> af, List<Room> afb, List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
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

        double benifit = 0;
        while (ll.size() > alloc) {
            int le = 0;
            int ln = 0;
            Room rF = null;
            Lesson lF = null;
            double d = Double.MAX_VALUE;


            for (Lesson l : ll
                    ) {

                if (!alloced[le]) {

                    for (Room r :
                            af) {
                        double d1 = r.newCost(l);

                        if (d1 < d && d1 >= 0) {
                            d = d1;
                            lF = l;
                            rF = r;
                            ln = le;
                        }
                        if (d1 == d && d1 >= 0) {
                            if (r.benifit(l) > rF.benifit(lF)) {
                                d = d1;
                                lF = l;
                                rF = r;
                                ln = le;
                            } else if (r.benifit(l) == rF.benifit(lF) && r.getCapacity() < rF.getCapacity()) {
                                d = d1;
                                lF = l;
                                rF = r;
                                ln = le;
                                comp++;
                            } else if (r.benifit(l) == rF.benifit(lF) && l.getStudents() > lF.getStudents()) {
                                d = d1;
                                lF = l;
                                rF = r;
                                ln = le;
                                comp++;

                            } else if (r.benifit(l) == rF.benifit(lF) && r.next(l) > r.next(lF)) {
                                d = d1;
                                lF = l;
                                rF = r;
                                ln = le;
                                comp++;
                            }
                        }


                    }


                }


                le++;

            }
            if (rF == null || lF == null)
                System.out.println("FAIL1");


            else if (rF.assing(lF)) {
                alloced[ln] = true;
                benifit += rF.benifit(lF) * lF.getLenght();

            } else {
                System.exit(1);
            }
            alloc++;


        }
        int i = 0;
        for (Lesson l :
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
            int ln = 0;
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
                            ln = le;
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
        int i = 0;
        for (Lesson l :
                ll) {
            if (!alloced[i])
                System.out.println(l.getStudents());
            i++;

        }
    }


    private static void run1(List<Room> af, List<Room> afb, List<Lesson> ll, List<Lesson> llb, List<Room> rp, List<Lesson> ls) {
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
        int s = 0;
        while (ll.size() > alloc) {
            Lesson l = ll.get(alloc);
            boolean assing = false;
            for (Room r :
                    af) {
                if (r.assing(l)) {
                    alloc++;
                    assing = true;
                    break;
                }
            }
            if (!assing) {
                System.out.println("FAIL");
                alloc++;
                s += l.getStudents();
                //return;
            }


        }
        System.out.println(s);
    }

    public static int spaceFails(List<Room> room) {
        int result = 0;
        for (int i = 0; i < room.size(); i++) {
            result += room.get(i).spaceFails();
        }
        return result;
    }

    public static int spaceFull(List<Room> room) {
        int result = 0;
        for (int i = 0; i < room.size(); i++) {
            result += room.get(i).stuff();
        }
        return result;
    }

    public static void GRASP(int MAX, List<Room> r, List<Lesson> l, int size) {
        List<Room> start = new ArrayList<>();
        List<Room> currentSol = new ArrayList<>();
        start = newInstance(r);
        r = Greedy(r, l, size);
        printCompact(r);
        r = Local(r);
        printCompact(r);

        System.out.println("Execution");

        for (int i = 1; i < MAX; i++) {
            currentSol = newInstance(start);
            System.out.println(i);
            currentSol = Greedy(currentSol, l, size);
            printCompact(r);

            currentSol = Local(currentSol);
            printCompact(r);

            if (eval(currentSol, r))
                r = new ArrayList<>(currentSol);

        }

    }

    private static void printCompact(List<Room> r) {
        System.out.println("=====================");
        int v = 0;
        for (int a = 0; a < r.size(); a++) {
            v += r.get(a).numberTransistion();
        }
        System.out.println("Valor:" + v);
        System.out.println("=====================");

    }

    private static List<Room> newInstance(List<Room> r) {
        List<Room> start = new ArrayList<>();
        for (Room item : r) {
            start.add(new Room(item.getCapacity(), 0, item.getName(), item.id));
        }
        return start;
    }

    private static boolean eval(List<Room> currentSol, List<Room> r) {
        int v = 0, v1 = 0;
        for (int i = 0; i < r.size(); i++) {
            v1 += r.get(i).numberTransistion();
        }
        for (int j = 0; j < currentSol.size(); j++) {
            v += currentSol.get(j).numberTransistion();
        }
        System.out.println((v1 > v) + " " + v1 + " New " + v);
        return v1 > v;
    }

    private static List<Room> Local(List<Room> currentSol) {
        return swampLecture(currentSol);
    }

    private static List<Room> Greedy(List<Room> af, List<Lesson> ll, int size) {
        boolean alloced[] = new boolean[ll.size()];
        List<Pair<Lesson, Room>> rcl = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        Arrays.fill(alloced, false);
        Room rF = null;
        Lesson lF = null;
        for (int i = 0; i < ll.size(); ) {
            int le = 0;
            double d = -Double.MAX_VALUE;
            for (Lesson l : ll
                    ) {
                if (!alloced[le]) {
                    for (Room r :
                            af) {
                        double d1 = r.cost(l);
                        if (rcl.size() < size) {
                            rcl.add(new Pair<>(l, r));
                            list.add(le);
                        } else {
                            int a = 0;
                            Boolean bol = false;
                            Pair<Lesson, Room> old = null;
                            for (Pair<Lesson, Room> l1 :
                                    rcl) {
                                d = l1.snd.cost(l1.fst);
                                if (!l.equals(l1.fst) && d1 >= d) {
                                    d = d1;
                                    lF = l;
                                    rF = r;
                                    bol = true;
                                    old = l1;
                                    break;
                                }
                                a++;
                            }
                            if (bol) {
                                list.remove(a);
                                list.add(le);
                                rcl.remove(old);
                                rcl.add(new Pair<>(lF, rF));

                            }
                        }
                    }


                }
                le++;
            }

            Random rand = new Random(System.currentTimeMillis());
            if (rcl.size() > 0) {
                int lecture = rand.nextInt(rcl.size());
                if (rcl.get(lecture).snd.assing(rcl.get(lecture).fst)) {
                    alloced[list.get(lecture)] = true;
                    i++;
                    list.remove(lecture);
                    rcl.remove(rcl.get(lecture));
                } else {

                }
            } else {
                i++;
            }
        }


        int i = 0;
        for (Lesson l :
                ll) {
            if (!alloced[i])
                // System.out.println(l.getStudents());
                i++;

        }
        return af;
    }

    static List<Room> swampLecture(List<Room> af) {
        boolean move = true;
        while (move) {
            boolean swamp = false;
            for (int i = 0; i < af.size(); i++) {
                for (int j = 1; j < af.size(); j++) {
                    if (af.get(i).getCapacity() == af.get(j).getCapacity()) {
                        for (int k = 0; k < Room.DAYS; k++) {
                            for (int l = 0; l < Room.SLOTS; l++) {
                                if (af.get(i).getLesson(k, l) != null) {
                                    if (af.get(j).free(k, l, af.get(i).getLesson(k, l).getLenght()) ||
                                            af.get(j).sameSize(k, l, af.get(i).getLesson(k, l).getLenght())) {
                                        if (af.get(i).tryswamp(af.get(j).getLesson(k, l),
                                                af.get(i).getLesson(k, l)) +
                                                af.get(j).tryswamp(af.get(i).getLesson(k, l),
                                                        af.get(j).getLesson(k, l)) < 0) {
                                            af.get(i).swamp(af.get(j).swamp(af.get(i).getLesson(k, l)));
                                            swamp = true;
                                            //     System.out.println(af.get(i).tryswamp(af.get(j).getLesson(k, l),
                                            //           af.get(i).getLesson(k, l)) +
                                            //         af.get(j).tryswamp(af.get(i).getLesson(k, l),
                                            //               af.get(j).getLesson(k, l)));
                                        }

                                    }
                                    l += (af.get(i).getLesson(k, l).getLenght() - 1);

                                }


                            }
                        }
                    }
                }
            }
            if (!swamp)
                move = false;
        }

        // printCompact(af);

        return af;

    }


}
