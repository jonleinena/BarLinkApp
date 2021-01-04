package com.example.barlink.utils;

import java.util.ArrayList;

public class Sorting {

    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortNum(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortNum(listLeft);
            listRight = mergeSortNum(listRight);

            return mergeNum(listLeft, listRight);
        }


    }

    public static <T extends I_Comparable<T>> ArrayList<T> mergeNum(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compareNum(b.get(0))) {
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

    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortStr(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortStr(listLeft);
            listRight = mergeSortStr(listRight);

            return mergeStr(listLeft, listRight);
        }


    }

    public static <T extends I_Comparable<T>> ArrayList<T> mergeStr(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compareStr(b.get(0))) {
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
