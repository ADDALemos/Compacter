package com.company;

import java.util.Comparator;

/**
 * Created by Alexandre on 24/06/2017.
 */
public class CompareBySize implements Comparator<Room>{

    @Override
    public int compare(Room o1, Room o2) {
        return Double.compare(o1.getCapacity(),o2.getCapacity());
    }
}
