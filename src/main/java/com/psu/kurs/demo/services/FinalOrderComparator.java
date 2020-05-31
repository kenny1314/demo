package com.psu.kurs.demo.services;

import com.psu.kurs.demo.entity.FinalOrder;

import java.util.Comparator;

public class FinalOrderComparator implements Comparator<FinalOrder> {

    @Override
    public int compare(FinalOrder f1, FinalOrder f2) {
        if (f1.getId() > f2.getId()) {
            return -1;
        } else if (f1.getId() < f2.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
