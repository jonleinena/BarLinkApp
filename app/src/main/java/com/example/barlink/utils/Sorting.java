package com.example.barlink.utils;

import java.util.ArrayList;

public class Sorting {
    public static <T extends I_Comparable<T>> ArrayList<T> mergeSort(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSort(listLeft);
            listRight = mergeSort(listRight);

            return merge(listLeft, listRight);
        }


    }

    public static <T extends I_Comparable<T>> ArrayList<T> merge(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compare(b.get(0))) {
                c.add(b.get(0));
                b.remove(0);
            } else {
                c.add(a.get(0));
                a.remove(0);
            }
        }
        //At this point either a or b is empty
        while (!a.isEmpty()) {
            c.add(a.get(0));
            a.remove(0);
        }
        while ((!b.isEmpty())) {
            c.add(b.get(0));
            b.remove(0);
        }
        return c;
    }


}
