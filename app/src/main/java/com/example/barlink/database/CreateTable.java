package com.example.barlink.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Class to create tables in the database
 *
 * @author sqlitetutorial.net
 * @author github.com/jonleinena
 * @author github.com/FerreMikel
 */
public class CreateTable {

    /**
     * Method to create a new table in the BarLink database.
     */
    public static void createTables(Connection conn) {

        try (Statement stmt = conn.createStatement()) {

            String sql0 = "CREATE TABLE ESTABLISHMENT IF NOT EXISTS(\n" +
                    "id INTEGER NOT NULL DEFAULT 'id=1' CHECK(id=1),\n" +
                    "NAME TEXT NOT NULL,\n" +
                    "NIF TEXT NOT NULL CHECK(legth(NIF)=9),\n" +
                    "EMAIL TEXT NOT NULL,\n" +
                    "TELEPHONE INTEGER NOT NULL,\n" +
                    "ADDRESS TEXT NOT NULL,\n" +
                    "PRIMARY KEY(id),\n" +
                    "CHECK()\n" +
                    ");";
            String sql1 = "CREATE TABLE IF NOT EXISTS ZONE (\n"
                    + "    idZone integer NOT NULL,\n"
                    + "    name text NOT NULL,\n"
                    + "    capacity real NOT NULL\n"
                    + ");";

            String sql2 = "CREATE TABLE IF NOT EXISTS TABLE_DB (\n"
                    + "    idTable integer NOT NULL,\n"
                    + "    idZone integer NOT NULL,\n"
                    + "    tableCapacity real NOT NULL, \n"
                    + "    FOREIGN KEY(idZone) REFERENCES ZONE(idZone),\n"
                    + "    PRIMARY KEY(idTable, idZone)\n"
                    + ");";

            String sql3 = "CREATE TABLE IF NOT EXISTS USER (\n"
                    + "    idUser integer NOT NULL PRIMARY KEY,\n"
                    + "    name text NOT NULL,\n"
                    + "    type text NOT NULL\n"
                    + ");";

            String sql4 = "CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORY (\n"
                    + "    idCategory integer PRIMARY KEY,\n"
                    + "    name text NOT NULL\n"
                    + ");";

            String sql5 = "CREATE TABLE IF NOT EXISTS MENU_PRODUCT (\n"
                    + "    idProduct integer,\n"
                    + "    idCategory integer NOT NULL,\n"
                    + "    idCommand integer NOT NULL,\n"
                    + "    name text NOT NULL,\n"
                    + "    price real NOT NULL,\n"
                    + "    description ingredients NOT NULL,\n"
                    + "    imagePath text NOT NULL,\n"
                    + "    FOREIGN KEY(idCategory) REFERENCES PRODUCT_CATEGORY(idCategory),\n"
                    + "    FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand),\n"
                    + "    PRIMARY KEY(idProduct, idCategory)\n"
                    + ");";
            String sql6 = "CREATE TABLE IF NOT EXISTS COMMAND (\n"
                    + "    idCommand integer NOT NULL,\n"
                    + "    idTable integer NOT NULL,\n"
                    + "    idProduct integer NOT NULL,\n"
                    + "    idUser integer NOT NULL,\n"
                    + "    hour DATETIME NOT NULL,\n"
                    + "    FOREIGN KEY(idTable) REFERENCES TABLE_DB(idTable),\n"
                    + "    FOREIGN KEY(idProduct) REFERENCES PRODUCT(idProduct),\n"
                    + "    FOREIGN KEY(idUser) REFERENCES USER(idUser),\n"
                    + "    PRIMARY KEY(idCommand, idTable, idProduct, idUser)\n"
                    + ");";
            String sql7 = "CREATE TABLE IF NOT EXISTS RECEIPT (\n"
                    + "    idReceipt integer NOT NULL,\n"
                    + "    idCommand integer NOT NULL,\n"
                    + "    hour DATETIME NOT NULL,\n"
                    + "    price real,\n"
                    + "    FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand),\n"
                    + "    PRIMARY KEY(idReceipt, idCommand)\n"
                    + ");";
            String sql8 = "CREATE TABLE IF NOT EXISTS WAREHOUSE_CATEGORY (\n"
                    + "    idWCategory integer NOT NULL PRIMARY KEY,\n"
                    + "    name text NOT NULL\n"
                    + ");";
            String sql9 = "CREATE TABLE IF NOT EXISTS WAREHOUSE_PRODUCT (\n"
                    + "    idWProduct integer NOT NULL,\n"
                    + "    idWCategory integer NOT NULL,\n"
                    + "    name text NOT NULL,\n"
                    + "    amount integer NOT NULL,\n"
                    + "    minimumAmount integer NOT NULL,\n"
                    + "    FOREIGN KEY(idWCategory) REFERENCES WAREHOUSE_CATEGORY(idWCategory),\n"
                    + "    PRIMARY KEY(idWProduct, idWCategory)\n"
                    + ");";
            String sql10 = "CREATE TABLE IF NOT EXISTS COMMAND_PRODUCT (\n" +
                    "idCommand INTEGER NOT NULL,\n" +
                    "idProduct INTEGER NOT NULL,\n" +
                    "PRIMARY KEY(dCommand, idProduct),\n" +
                    "FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY(idProduct) REFERENCES MENU_PRODUCT(idProduct) ON DELETE CASCADE\n" +
                    ");";

            // Create a the tables
            stmt.execute(sql0);
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
            stmt.execute(sql5);
            stmt.execute(sql6);
            stmt.execute(sql7);
            stmt.execute(sql8);
            stmt.execute(sql9);
            stmt.execute(sql9);
            stmt.execute(sql10);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
