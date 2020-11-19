package com.example.barlink.establishment;

import java.util.ArrayList;

/**
 * Represents a Represents a zone
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class Zone {

    int idZone;
    String name;

    /**
     * Empty constructor
     */
    public Zone() {
        idZone = 0;
        name = "";
    }

    /**
     * Constructor based on id an capacity alone. tables is set to null
     *
     * @param idZone   zone's unique identifier
     * @param name     zone's capacity
     */
    public Zone(int idZone, String name) {
        this.idZone = idZone;
        this.name = name;
    }


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
}