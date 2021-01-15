package com.example.sorting;


import com.example.barlink.command.User;
import com.example.barlink.utils.sorting.Sorting;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Testing class to check the correct functioning of the Generic Mergesort and Quicksort methods
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class SortingTest {
    private User u1;
    private User u2;
    private User u3;
    private ArrayList<User> users;

    @Before
    public void setup() {
        this.u1 = new User("Jon", 2, "");
        this.u2 = new User("Alvaro", 1, "");
        this.u3 = new User("Mikel", 0, "");
        users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
    }

    @Test
    public void testMergeSortNum() {
        users = Sorting.mergeSortNum(users);
        assertEquals(u3, users.get(0));
    }

    @Test
    public void testMergeSortStr() {
        users = Sorting.mergeSortStr(users);
        assertEquals(u2, users.get(0));
    }

    @Test
    public void testQuickSortStr() {
        Sorting.quicksortStr(users, 0, users.size());
        assertEquals(u2, users.get(0));
    }
}
