package com.lh.study.java.Collection;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListAndMap {

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        System.out.println(arrayList.size());
        arrayList.add(1);
        System.out.println(arrayList.size());
        arrayList.add(1);
        System.out.println(arrayList.size());
        arrayList.add(1);
        System.out.println(arrayList.size());

        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        Vector vector = new Vector();
    }

}
