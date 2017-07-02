package com.company;

import java.util.Comparator;

/**
 * Created by Alexandre on 24/06/2017.
 */
public class CompareByStart implements Comparator<Lesson>{

    @Override
    public int compare(Lesson o1, Lesson o2) {
        return Double.compare(o1.getStart(),o2.getStart());
      /* int d =Double.compare(o1.getStart(),o2.getStart());
        if(d!=0)
            return d;
        d =Double.compare(o1.getLenght(),o2.getLenght());
        return d;*/

    }
}
