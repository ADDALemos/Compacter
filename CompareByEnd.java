package com.company;

import java.util.Comparator;

/**
 * Created by Alexandre on 24/06/2017.
 */
public class CompareByEnd implements Comparator<Lesson>{

    @Override
    public int compare(Lesson o1, Lesson o2) {
        return Double.compare(o1.getEnd(),o2.getEnd());


    }
}
