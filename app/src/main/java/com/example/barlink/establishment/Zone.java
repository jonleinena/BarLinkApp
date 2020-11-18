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
    Table[] tables;
    int capacity;

    /**
     * Empty constructor
     */
    public Zone() {
        idZone = 0;
        tables = null;
        capacity = 0;
    }

    /**
     * Constructor based on id an capacity alone. tables is set to null
     * @param idZone zone's unique identifier
     * @param capacity zone's capacity
     */
    public Zone(int idZone, int capacity) {
        this.idZone = idZone;
        tables = null;
        this.capacity = capacity;
    }

    /**
     * Zone class constructor
     *
     * @param idZone zone identifier
     * @param tables array of tables
     * @param capacity capacity of the zone, to determine how many tables fit.
     */
    public Zone(int idZone, Table[] tables, int capacity) {
        this.idZone = idZone;
        this.tables = tables;
        this.capacity = capacity;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Method to add a table to the zone
     *
     * @param table Table class object, the table to be added to the zone
     */
    public void addTable(Table table, int position) {
        tables[position] = table;
    }

    /**
     * Method to remove a table from the zone
     *
     * @param table Table class object, the table to be removed from the zone
     */
    public void removeTable(Table table, int position) {
        tables[position] = null;
    }
}
