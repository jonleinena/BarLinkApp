package com.example.barlink.establishment;

import com.example.barlink.command.User;
import com.example.barlink.utils.sorting.I_Comparable;
import com.google.android.material.transition.ScaleProvider;

import java.util.ArrayList;

/**
 * Represents a Represents a zone
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class Zone implements I_Comparable<Zone> {

    int idZone;
    String name;
    int capacity;



    /**
     * Empty constructor
     */
    public Zone() {
        idZone = 0;
        name = "";
        capacity = 0;
    }

    /**
     * Constructor based on id an capacity alone. tables is set to null
     *
     * @param idZone   zone's unique identifier
     * @param name     zone's capacity
     */
    public Zone(int idZone, String name, int  capacity) {
        this.idZone = idZone;
        this.name = name;
        this.capacity = capacity;    }


    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean compareNum(Zone o) {
        return this.idZone>o.getIdZone();
    }

    @Override
    public boolean compareStr(Zone o) {
        return (int)this.name.toLowerCase().charAt(0)>(int)o.getName().toLowerCase().charAt(0);
    }
}