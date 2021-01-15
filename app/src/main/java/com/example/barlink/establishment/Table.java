package com.example.barlink.establishment;

import com.example.barlink.command.User;
import com.example.barlink.utils.sorting.I_Comparable;

/**
 * Represents a table
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class Table {
    int idTable;
    boolean booked;
    int idZone;

    /**
     * Empty constructor
     */
    public Table() {
        this.idTable = 0;
        this.booked = false;
        this.idZone = 0;
    }

    /**
     * Table class constructor based on the id, capacity is set to 4 as default.
     *
     * @param idTable Represents the table's id number
     */
    public Table(int idTable, int idZone) {
        this.idTable = idTable;
        this.booked = false;
        this.idZone = idZone;
    }

    /**
     * Table class constructor
     *
     * @param idTable   Represents the table's id number
     * @param booked    Represents whether the table is booked or not
     */
    public Table(int idTable, boolean booked, int idZone) {
        this.idTable = idTable;
        this.booked = booked;
        this.idZone = idZone;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    /**
     * Change the status of the table to booked
     */
    public void bookTable() {
        this.booked = true;
    }

    /**
     * Change the status of the table to unbooked
     */
    public void unbookTable() {
        this.booked = false;
    }

    public boolean tableStatus() {
        return this.booked;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }
}
