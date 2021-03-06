package com.company; /**
 * Created by alexandrelemos on 22/11/2017.
 */

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.util.List;

public class ILPrun {
    public static final int DAY = 5;
    public static final int TIMESLOT = 26;
    public static IloCplex cplex;
    static int a[][][];

    public static void runReview(int roomNumber, int lessonNumber, List<com.company.Lesson> lessonSet, List<com.company.Room> roomCAP) {
        double time = 4000568;
        try {
            cplex = createIloCplex();
            cplex.setParam(IloCplex.Param.TimeLimit, time);
            IloNumVar[][] room = defineLectureRoom(roomNumber, lessonNumber, cplex);
            standartEncoding(roomNumber, lessonSet, cplex, room, null);

            IloNumExpr temp = students(roomNumber, lessonSet, roomCAP, cplex, room);

            cplex.addMaximize(temp);
            if (cplex.solve()) {
                double val = cplex.getObjValue();
                System.out.println(cplex.getObjValue());

                cplex.clearModel();

                cplex.end();


                cplex = createIloCplex();

                IloCplex.ParameterSet set = new IloCplex.ParameterSet();
                set.setParam(IloCplex.Param.TimeLimit, time);

                cplex.setParameterSet(set);


                room = defineLectureRoom(roomNumber, lessonNumber, cplex);


                standartEncoding(roomNumber, lessonSet, cplex, room, null);

                temp = students(roomNumber, lessonSet, roomCAP, cplex, room);

                compact(roomNumber, lessonNumber, cplex, room, lessonSet);

                cplex.addEq(temp, val);


                cplex.exportModel("test.lp");//

                if (cplex.solve()) {
                    System.out.println(cplex.getObjValue());
                }


            }


        } catch (IloException e) {
            e.printStackTrace();
        }


    }

    public static void run(int roomNumber, int lessonNumber, List<com.company.Lesson> lessonSet, List<com.company.Room> roomCAP) {
        int max = 0;
        double time = 4000568;
        try {
            cplex = createIloCplex();
            cplex.setParam(IloCplex.Param.TimeLimit, time);//1924 36000


            // Create start variables

            IloNumVar[][][][] room = defineRoom(roomNumber, lessonNumber, cplex);
            // IloNumVar[][][] lesson = defineLesson(lessonNumber, cplex);
            //forceRoom(lessonSet,roomCAP,cplex,room);


            standartEncoding(roomNumber, lessonSet, cplex, room, null);

            //10


            if (max == 1) {
                //Seat everyone & compact
                for (com.company.Lesson l :
                        lessonSet) {
                    for (int j = 0; j < roomNumber; j++) {
                        for (int i = 0; i < l.getLenght(); i++) {
                            cplex.addGe(roomCAP.get(j).getCapacity(),
                                    cplex.prod(l.getStudents(), room[l.getId()][j][l.getDay()][l.getStart() + i]));
                        }
                    }
                }
                compact(roomNumber, lessonNumber, cplex, room, lessonSet);
                //cplex.exportModel("test.lp");
            }
            if (max == 0) {
                //10
                //studentsAlfa(roomNumber, lessonSet, roomCAP, cplex, room, 0.1);
                IloNumExpr temp = students(roomNumber, lessonSet, roomCAP, cplex, room);

                cplex.addMaximize(temp);
                //cplex.exportModel("test.lp");
            }
            if (cplex.solve()) {
                double[][][][] value = out(roomNumber, lessonNumber, lessonSet, roomCAP, cplex, room);

                double val = cplex.getObjValue();
                //cplex.exportModel("testold.lp");
                cplex.clearModel();

                cplex.end();


                cplex = createIloCplex();

                IloCplex.ParameterSet set = new IloCplex.ParameterSet();
                set.setParam(IloCplex.Param.TimeLimit, time);
                set.setParam(IloCplex.Param.Preprocessing.RepeatPresolve  ,3);
                cplex.setParameterSet(set);
                // cplex.setParam(IloCplex.Param.TimeLimit, time);

                // Create start variables

//                forceRoom(lessonSet,roomCAP,cplex,room);


                room = defineRoom(roomNumber, lessonNumber, cplex);


                standartEncoding(roomNumber, lessonSet, cplex, room, null);

                IloNumExpr temp = students(roomNumber, lessonSet, roomCAP, cplex, room);
                //studentsAlfa(roomNumber, lessonSet, roomCAP, cplex, room, .43);

                compact(roomNumber, lessonNumber, cplex, room, lessonSet);
                //  cplex.exportModel("test1.lp");

                //cplex.exportModel("mo1.lp");
                //  IloNumVar[][][] lesson1 = defineLesson(lessonNumber, cplex1);
                warmStart(roomNumber, lessonNumber, cplex, room, value);

                //MultipleObjectiveException
                cplex.addEq(temp, val);
                // cplex1.addMaximize(temp);

                //cplex1.addEq((int)val,temp);
                // cplex.use(new Callback());
                cplex.exportModel("test1.lp");


                if (cplex.solve()) {
                    out(roomNumber, lessonNumber, lessonSet, roomCAP, cplex, room);


                }
                cplex.end();


            } else {
                System.out.println("error");
                cplex.end();
            }

            //obter valor e recorrer

        } catch (IloException e) {
            e.printStackTrace();
        }
        System.out.println("END");

    }

    private static void warmStart(int roomNumber, int lessonNumber, IloCplex cplex, IloNumVar[][][][] room, double[][][][] value) {
        IloNumVar[] startVar = new IloNumVar[lessonNumber * roomNumber * 5 * 26];
        double[] startVal = new double[lessonNumber * roomNumber * 5 * 26];
        int idx = 0;
        for (int k = 0; k < lessonNumber; k++) {
            for (int i = 0; i < roomNumber; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int l = 0; l < 26; l++) {
                        startVar[idx] = room[k][i][j][l];
                        startVal[idx] = value[k][i][j][l];
                        ++idx;
                    }
                }
            }
        }
        try {
            System.out.println(idx + " " + lessonNumber * roomNumber * 5 * 26);
            cplex.addMIPStart(startVar, startVal, "addMIPStart start");
        } catch (IloException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void forceRoom(List<Lesson> ll, List<Room> rr, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        for (Lesson l :
                ll) {
            for (int j=0; j<rr.size();j++) {
                if (rr.get(j).id.equals(l.originalRoom)) {
                    if(rr.get(j).getCapacity()<l.getStudents()){
                        l.setStudents(rr.get(j).getCapacity());
                        for (int i = 0; i < l.getLenght(); i++) {
                            cplex.addEq(room[l.getId()][j][l.getDay()][l.getStart() + i],1);
                        }

                    }
                }
            }

        }
    }

    private static double[][][][] out(int roomNumber, int lessonNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        double[][][][] value = new double[lessonNumber][roomNumber][5][26];
        for (int i = 0; i < lessonNumber; i++) {
            for (int j = 0; j < roomNumber; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 26; l++) {
                        value[i][j][k][l] = 0;
                    }
                }
            }
        }
        for (int j = 0; j < roomNumber; j++) {

            for (Lesson l :
                    lessonSet) {
                for (int i = 0; i < l.getLenght(); i++) {
                    //for (int k = 0; k < 26; k++) {
                    if (cplex.getValue(room[l.getId()][j][l.getDay()][l.getStart() + i]) != 0) {
                        int v = i + l.getStart();
                        value[l.getId()][j][l.getDay()][l.getStart() + i] = cplex.getValue(room[l.getId()][j][l.getDay()][l.getStart() + i]);
                        System.out.print((l.getStudents() > roomCAP.get(j).getCapacity() ?
                                l.getName()+";Roomname;"+roomCAP.get(j).getName()+";Std;"+ l.getStudents()
                                        +";cap;"+roomCAP.get(j).getCapacity()+"\n" : ""));
                      //  System.out.println(l.getName() + " d" + l.getDay() + " j" + roomCAP.get(j).getName() + " slot" + v + " sol:" +
                               // cplex.getValue(room[l.getId()][j][l.getDay()][l.getStart() + i]));
                    }

                    //}
                }
                /*if(cplex.getValue(lesson[l.getId()][l.getDay()][l.getStart()+i])!=0)
                    System.out.println(l.getName()+" d"+l.getDay()+" i"+i+" k"+l.getStart()+" sol:"+
                            cplex.getValue(lesson[l.getId()][l.getDay()][l.getStart()+i]));
            */
            }
        }
        System.out.println("Solution status: " + cplex.getStatus());
        System.out.println(" Optimal Value = " + cplex.getObjValue());
        int comp = 0;
        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 1; k < 26; k++) {
                    double v = 0;
                    for (int l = 0; l < lessonNumber; l++) {
                        // if (k < lessonSet.get(l).getStart() || k >= (lessonSet.get(l).getStart() + lessonSet.get(l).getLenght()) || i != lessonSet.get(l).getDay()) {
                        //
                        //}
                        ///  else{
                        //   if(cplex.getValue(room[l][j][i][k - 1])!=0||cplex.getValue(room[l][j][i][k ])!=0)
                        // System.out.println(cplex.getValue(room[l][j][i][k - 1])+" now "+cplex.getValue(room[l][j][i][k ])+" slot "+k+" "+l+" room  "+j+" day "+i+ " slot for lesson"+lessonSet.get(l).getStart()+" lesson cap"+lessonSet.get(l).getStudents()+" room cap"+roomCAP.get(j).getCapacity()+" lesson day "+lessonSet.get(l).getDay());
                        v += cplex.getValue(room[l][j][i][k - 1]);
                        v += cplex.getValue(room[l][j][i][k]);
                        //}
                    }
                    //System.out.println("v" + v);
                    if (v == 1)
                        comp++;

                }

            }
        }
        System.out.println("COMP" + comp);
        return value;
    }

    private static void compact(int roomNumber, int lessonNumber, IloCplex cplex, IloNumVar[][][][] room, List<Lesson> lessonSet) throws IloException {
        IloNumExpr min = cplex.numExpr();
        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < 5; i++) {
            for (int k = 1; k < 26; k++) {
                IloNumExpr behind = cplex.numExpr();
                int after = -1, befor = -1;
                // IloNumExpr infront = cplex.numExpr();
                    for (int l = 0; l < lessonNumber; l++
                            ) {
                        //infront = cplex.sum(infront, room[l][j][lessonSet.get(l).getDay()][k - 1], room[l][j][lessonSet.get(l).getDay()][k]);

                        // min = cplex.sum(min, cplex.prod(room[l][j][i][k + 1],  room[l][j][i][k]));
                        //infront=cplex.or(cplex.eq(1,infront),cplex.eq(1,room[l.getId()][j][i][k1]));
                        if (k < lessonSet.get(l).getStart() || k >= (lessonSet.get(l).getStart() + lessonSet.get(l).getLenght()) || i != lessonSet.get(l).getDay()) {
                            //
                        } else {
                            behind = cplex.sum(behind, room[l][j][i][k]);
                            after++;
                        }
                        if ((k - 1) < lessonSet.get(l).getStart() || (k - 1) >= (lessonSet.get(l).getStart() + lessonSet.get(l).getLenght()) || i != lessonSet.get(l).getDay()) {
                            //
                        } else {
                            behind = cplex.sum(behind, room[l][j][i][k - 1]);
                            befor++;
                        }

                        // infront = cplex.sum(infront, room[l][j][lessonSet.get(l).getDay()][k]);



                    }
                if (after != -1 && befor != -1)
                    min = cplex.sum(min, cplex.prod(1, cplex.eq(behind, 1)));



                    //  cplex.sum(min, cplex.abs(cplex.sum(cplex.prod(infront, -1), behind)));

                            /*cplex.or(
                            cplex.and(cplex.eq(1,infront),cplex.not(cplex.eq(1,behind))),
                            cplex.and(cplex.not(cplex.eq(1,infront)),cplex.eq(1,behind))
                            ));*/

                }
            }
        }
        cplex.addMinimize(min);


    }

    private static void compact(int roomNumber, int lessonNumber, IloCplex cplex, IloNumVar[][] room, List<Lesson> lessonSet) throws IloException {
        IloNumExpr min = cplex.numExpr();
        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < DAY; i++) {
                for (int k = 1; k < TIMESLOT; k++) {
                    IloNumExpr behind = cplex.numExpr();
                    int after = -1, befor = -1;
                    for (int l = 0; l < lessonNumber; l++
                            ) {
                        if (k < lessonSet.get(l).getStart() || k >= (lessonSet.get(l).getStart() + lessonSet.get(l).getLenght()) || i != lessonSet.get(l).getDay()) {
                            //
                        } else {
                            behind = cplex.sum(behind, cplex.prod(room[l][j], a[l][i][k]));
                            after++;
                        }
                        if ((k - 1) < lessonSet.get(l).getStart() || (k - 1) >= (lessonSet.get(l).getStart() + lessonSet.get(l).getLenght()) || i != lessonSet.get(l).getDay()) {
                            //
                        } else {
                            behind = cplex.sum(behind, cplex.prod(room[l][j], a[l][i][k - 1]));
                            befor++;
                        }

                        // infront = cplex.sum(infront, room[l][j][lessonSet.get(l).getDay()][k]);


                    }
                    if (after != -1 && befor != -1)
                        min = cplex.sum(min, cplex.prod(1, cplex.eq(behind, 1)));

                }
            }
        }
        cplex.addMinimize(min);


    }

    private static IloNumExpr students(int roomNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][] room) throws IloException {
        IloNumExpr temp = cplex.numExpr();
        for (Lesson l :
                lessonSet) {
            for (int j = 0; j < roomNumber; j++) {
                for (int i = 0; i < l.getLenght(); i++) {
                    if (roomCAP.get(j).getCapacity() >= l.getStudents())
                        temp = cplex.sum(temp,
                                cplex.prod(l.getStudents(), cplex.prod(room[l.getId()][j], a[l.getId()][l.getDay()][l.getStart() + i])));
                    else
                        temp = cplex.sum(temp,
                                cplex.prod(roomCAP.get(j).getCapacity(), cplex.prod(room[l.getId()][j], a[l.getId()][l.getDay()][l.getStart() + i])));
                }
            }
        }
        return temp;
    }

    private static IloNumExpr students(int roomNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        IloNumExpr temp = cplex.numExpr();
        for (Lesson l :
                lessonSet) {
            for (int j = 0; j < roomNumber; j++) {
                for (int i = 0; i < l.getLenght(); i++) {
                    if (roomCAP.get(j).getCapacity() >= l.getStudents())
                        temp = cplex.sum(temp,
                                cplex.prod(l.getStudents(), room[l.getId()][j][l.getDay()][l.getStart() + i]));
                    else
                        temp = cplex.sum(temp,
                                cplex.prod(roomCAP.get(j).getCapacity(), room[l.getId()][j][l.getDay()][l.getStart() + i]));
                }
            }
        }
        return temp;
    }

    private static void studentsAlfa(int roomNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][][][] room, double alfa) throws IloException {
        for (Lesson l :
                lessonSet) {
            for (int j = 0; j < roomNumber; j++) {
                for (int i = 0; i < l.getLenght(); i++) {
                    cplex.addGe(roomCAP.get(j).getCapacity(),
                            cplex.prod(l.getStudents()-(l.getStudents() * alfa), room[l.getId()][j][l.getDay()][l.getStart() + i]));

                }
            }
        }
    }

    private static void standartEncoding(int roomNumber, List<Lesson> lessonSet, IloCplex cplex, IloNumVar[][] room, IloNumVar[][][] lesson) throws IloException {
        a = new int[lessonSet.size()][DAY][TIMESLOT];
        for (int i = 0; i < lessonSet.size(); i++)
            for (int j = 0; j < DAY; j++) {
                for (int k = 0; k < TIMESLOT; k++) {
                    a[i][j][k] = 0;
                }

            }
        for (Lesson l :
                lessonSet) {
            for (int i = 0; i < l.getLenght(); i++) {
                a[l.getId()][l.getDay()][l.getStart() + i] = 1;

            }

        }


        //not more than one room
        for (Lesson l :
                lessonSet) {
            for (int i = 0; i < l.getLenght(); i++) {
                IloNumExpr temp = cplex.numExpr();
                for (int j = 0; j < roomNumber; j++) {
                    temp = cplex.sum(temp, room[l.getId()][j]);
                }
                cplex.addEq(1, temp);
            }

        }
        //one per room

        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < DAY; i++) {
                for (int k = 0; k < TIMESLOT; k++) {
                    IloNumExpr temp = cplex.numExpr();
                    for (Lesson l :
                            lessonSet) {
                        temp = cplex.sum(temp, cplex.prod(room[l.getId()][j], a[l.getId()][i][k]));


                    }
                    cplex.addGe(1, temp);//<=

                }
            }

        }

    }

    private static void standartEncoding(int roomNumber, List<Lesson> lessonSet, IloCplex cplex, IloNumVar[][][][] room, IloNumVar[][][] lesson) throws IloException {
        //Active start time
       /* for (Lesson l :
                lessonSet) {
            //for (int i = 0; i < l.getDuration(); i++) {
            //    cplex.eq(1,lesson[l.getId()][l.getSlot()+i][l.getDay()]);
            // }
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 26; i++) {
                    if (i < l.getStart() || i >= (l.getStart() + l.getLenght()) || j != l.getDay()) {
                        cplex.addEq(0, lesson[l.getId()][j][i]);
                    } else {
                        cplex.addEq(1, lesson[l.getId()][j][i]);
                    }
                }
                //  System.out.println("i"+i+" start"+l.getStart()+" id"+l.getId()+" day"+l.getDay());

            }

        }*/

        //not more than one room
        for (Lesson l :
                lessonSet) {
            for (int i = 0; i < l.getLenght(); i++) {
                IloNumExpr temp = cplex.numExpr();
                for (int j = 0; j < roomNumber; j++) {
                    temp = cplex.sum(temp, room[l.getId()][j][l.getDay()][l.getStart() + i]);
                }
                cplex.addEq(1, temp);
            }

        }
        // in the same room
        for (Lesson l :
                lessonSet) {
            for (int j = 0; j < roomNumber; j++) {
                IloNumExpr temp = cplex.numExpr();
                for (int i = 0; i < l.getLenght(); i++) {
                    temp = cplex.sum(temp, room[l.getId()][j][l.getDay()][l.getStart() + i]);
                }
                cplex.addEq(cplex.prod(room[l.getId()][j][l.getDay()][l.getStart()], l.getLenght()), temp);
                // cplex.add(cplex.or(cplex.eq(l.getLenght(), temp), cplex.eq(0, temp)));//ou esta um ou estao todos

            }

        }
        //one per room

        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < 26; k++) {
                    IloNumExpr temp = cplex.numExpr();
                    for (Lesson l :
                            lessonSet) {
                        //System.out.println(""+l.getId()+" "+j+" "+i+" "+k);
                        temp = cplex.sum(temp, room[l.getId()][j][i][k]);

                    }
                    cplex.addGe(1, temp);//<=

                }
            }

        }
    }

    private static IloNumVar[][][] defineLesson(int lessonNumber, IloCplex cplex) throws IloException {
        IloNumVar[][][] lesson = new IloNumVar[lessonNumber][5][];
        for (int k = 0; k < lessonNumber; k++)
            for (int j = 0; j < 5; j++)
                lesson[k][j] = cplex.boolVarArray(26);
        return lesson;
    }

    private static IloNumVar[][] defineLectureRoom(int roomNumber, int lessonNumber, IloCplex cplex) throws IloException {
        IloNumVar[][] room = new IloNumVar[lessonNumber][roomNumber];
        for (int k = 0; k < lessonNumber; k++)
            for (int i = 0; i < roomNumber; i++)
                room[k][i] = cplex.boolVar();

        return room;
    }

    private static IloNumVar[][][][] defineRoom(int roomNumber, int lessonNumber, IloCplex cplex) throws IloException {
        IloNumVar[][][][] room = new IloNumVar[lessonNumber][roomNumber][5][];
        for (int k = 0; k < lessonNumber; k++)
            for (int i = 0; i < roomNumber; i++)
                for (int j = 0; j < 5; j++)
                    room[k][i][j] = cplex.boolVarArray(26);
        return room;
    }

    private static IloCplex createIloCplex() throws IloException {
        return new IloCplex();
    }

    private static class Callback extends IloCplex.MIPInfoCallback {
        private final double _timeStart;

        Callback() throws IloException {
            _timeStart = cplex.getCplexTime();
        }

        @Override
        protected void main() throws IloException {
            System.out.println("Time " + (cplex.getCplexTime() - _timeStart));
            System.out.println("Sol " + hasIncumbent());
            System.out.println("Value " + getIncumbentObjValue());
        }

    }


}
