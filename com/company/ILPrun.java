package com.company; /**
 * Created by alexandrelemos on 22/11/2017.
 */

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.util.List;

public class ILPrun {

    public static void run(int roomNumber, int lessonNumber, List<com.company.Lesson> lessonSet, List<com.company.Room> roomCAP) {
        int max = 0;
        try {
            IloCplex cplex = createIloCplex();
            // Create start variables

            IloNumVar[][][][] room = defineRoom(roomNumber, lessonNumber, cplex);
            // IloNumVar[][][] lesson = defineLesson(lessonNumber, cplex);
            standartEncoding(roomNumber, lessonSet, cplex, room, null);

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
                compact(roomNumber, lessonNumber, cplex, room);
                //cplex.exportModel("test.lp");
            }
            if (max == 0) {
                IloNumExpr temp = students(roomNumber, lessonSet, roomCAP, cplex, room);

                cplex.addMaximize(temp);
                //cplex.exportModel("test.lp");
            }
            if (cplex.solve()) {
                out(roomNumber, lessonNumber, lessonSet, roomCAP, cplex, room);

                double val = cplex.getObjValue();
                cplex.end();

                IloCplex cplex1 = createIloCplex();
                // Create start variables

                IloNumVar[][][][] room1 = defineRoom(roomNumber, lessonNumber, cplex1);
                //  IloNumVar[][][] lesson1 = defineLesson(lessonNumber, cplex1);
                standartEncoding(roomNumber, lessonSet, cplex1, room1, null);

                IloNumExpr temp = students(roomNumber, lessonSet, roomCAP, cplex1, room1);
                //MultipleObjectiveException
                cplex1.addLe(temp, val);
                //cplex1.addEq((int)val,temp);

                // compact(roomNumber, lessonNumber, cplex1, room1);

                if (cplex1.solve()) {
                    out(roomNumber, lessonNumber, lessonSet, roomCAP, cplex1, room1);


                }
                cplex1.end();


            } else {
                System.out.println("error");
                cplex.end();
            }
            //obter valor e recorrer

        } catch (IloException e) {
            e.printStackTrace();
        }

    }

    private static void out(int roomNumber, int lessonNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        for (int j = 0; j < roomNumber; j++) {

            for (Lesson l :
                    lessonSet) {
                for (int i = 0; i < l.getLenght(); i++) {
                    //for (int k = 0; k < 26; k++) {
                    if (cplex.getValue(room[l.getId()][j][l.getDay()][l.getStart() + i]) != 0) {
                        int v = i + l.getStart();
                        System.out.println(l.getName() + " d" + l.getDay() + " j" + roomCAP.get(j).getName() + " slot" + v + " sol:" +
                                cplex.getValue(room[l.getId()][j][l.getDay()][l.getStart() + i]));
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
                    int v = 0;
                    for (int l = 0; l < lessonNumber; l++) {
                        v += cplex.getValue(room[l][j][i][k - 1]);
                        v += cplex.getValue(room[l][j][i][k]);
                    }
                    if (v == 0)
                        comp++;

                }

            }
        }
        System.out.println(comp);
    }

    private static void compact(int roomNumber, int lessonNumber, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        IloNumExpr min = cplex.numExpr();
        for (int j = 0; j < roomNumber; j++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < 25; k++) {
                    // IloNumExpr behind = cplex.numExpr();
                    // IloNumExpr infront = cplex.numExpr();
                    for (int l = 0; l < lessonNumber; l++
                            ) {
                        //  infront = cplex.sum(infront, room[l][j][i][k+1]);
                        min = cplex.sum(min, cplex.abs(cplex.sum(cplex.prod(room[l][j][i][k + 1], -1), room[l][j][i][k])));
                        //infront=cplex.or(cplex.eq(1,infront),cplex.eq(1,room[l.getId()][j][i][k1]));
                        //behind=cplex.or(cplex.eq(1,behind),cplex.eq(1,room[l.getId()][j][i][k]));


                    }

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

    private static IloNumExpr students(int roomNumber, List<Lesson> lessonSet, List<Room> roomCAP, IloCplex cplex, IloNumVar[][][][] room) throws IloException {
        IloNumExpr temp = cplex.numExpr();
        for (Lesson l :
                lessonSet) {
            for (int j = 0; j < roomNumber; j++) {
                for (int i = 0; i < l.getLenght(); i++) {
                    temp = cplex.sum(temp, cplex.prod(cplex.ge(roomCAP.get(j).getCapacity(),
                            cplex.prod(l.getStudents(), room[l.getId()][j][l.getDay()][l.getStart() + i])),
                            cplex.prod(l.getStudents(), room[l.getId()][j][l.getDay()][l.getStart() + i])),
                            cplex.prod(cplex.not(cplex.ge(roomCAP.get(j).getCapacity(),
                                    cplex.prod(l.getStudents(), room[l.getId()][j][l.getDay()][l.getStart() + i]))),
                                    cplex.prod(roomCAP.get(j).getCapacity(), room[l.getId()][j][l.getDay()][l.getStart() + i])));

                }
            }
        }
        return temp;
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

}
