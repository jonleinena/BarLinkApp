package com.example.barlink.establishment;

/**
 * Represents a table
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class Table {
    int idTable;
    boolean booked;
    int capacity;

    /**
     * Empty constructor
     */
    public Table() {
        this.idTable = 0;
        this.booked = false;
        this.capacity = 0;
    }

    /**
     * Table class constructor based on the id, capacity is set to 4 as default.
     *
     * @param idTable Represents the table's id number
     */
    public Table(int idTable) {
        this.idTable = 0;
        this.booked = false;
        this.capacity = 4;
    }

    /**
     * Table class constructor based on the id and capacity.
     *
     * @param idTable Represents the table's id number
     * @param capacity Represents the table's capacity.
     */
    public Table(int idTable, int capacity) {
        this.idTable = 0;
        this.booked = false;
        this.capacity = capacity;
    }

    /**
     * Table class constructor
     *
     * @param idTable   Represents the table's id number
     * @param booked    Represents whether the table is booked or not
     * @param capacity  Represents the tale's capacity
     */
    public Table(int idTable, boolean booked, int capacity) {
        this.idTable = idTable;
        this.booked = booked;
        this.capacity = capacity;
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
}
