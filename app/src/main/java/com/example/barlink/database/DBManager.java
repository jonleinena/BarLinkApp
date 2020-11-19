package com.example.barlink.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.barlink.command.User;
import com.example.barlink.establishment.Establishment;
import com.example.barlink.establishment.Table;
import com.example.barlink.establishment.Zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**claass to manage all the access to the database
 * @author github.com/jonleinena
 * @author github.com/FerreMikel
 */
public class DBManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BarLink.db";
    private static DBManager sInstance;

    /**
     *static method to get the instance of the class that has been previously created, to avoid creating a new one.
     * @param context context of the app
     * @return the instance of the class
     */
    public static synchronized DBManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBManager(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor of the class
     * @param context context of the app
     */
    private DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Always executes the create table statements in the onCreate Bundle, in case they are not created yet.
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ESTABLISHMENT (" +
                "id INTEGER NOT NULL DEFAULT 'id=1' CHECK(id=1) PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "nif TEXT NOT NULL CHECK(length(NIF)=9), " +
                "email TEXT NOT NULL, " +
                "phone INTEGER NOT NULL, " +
                "address TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE USER (" +
                "idUser INTEGER NOT NULL PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE ZONE(" +
                "idZone integer NOT NULL," +
                "name TEXT"+
                ")");
        db.execSQL("CREATE TABLE TABLE_DB (" +
                "idTable integer NOT NULL," +
                "idZone integer NOT NULL," +
                "tableCapacity real NOT NULL," +
                "FOREIGN KEY(idZone) REFERENCES ZONE(idZone)," +
                "PRIMARY KEY(idTable, idZone)" +
                ")");
        db.execSQL("CREATE TABLE PRODUCT_CATEGORY (" +
                "idCategory integer PRIMARY KEY," +
                "name text NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE MENU_PRODUCT (" +
                "idProduct integer," +
                "idCategory integer NOT NULL," +
                "idCommand integer NOT NULL," +
                "name text NOT NULL," +
                "price real NOT NULL," +
                "description ingredients NOT NULL," +
                "imagePath text NOT NULL," +
                "FOREIGN KEY(idCategory) REFERENCES PRODUCT_CATEGORY(idCategory)," +
                "FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand)," +
                "PRIMARY KEY(idProduct, idCategory)" +
                ")");
        db.execSQL("CREATE TABLE COMMAND (" +
                "idCommand integer NOT NULL," +
                "idTable integer NOT NULL," +
                "idProduct integer NOT NULL," +
                "idUser integer NOT NULL," +
                "hour DATETIME NOT NULL," +
                "FOREIGN KEY(idTable) REFERENCES TABLE_DB(idTable)," +
                "FOREIGN KEY(idProduct) REFERENCES PRODUCT(idProduct)," +
                "FOREIGN KEY(idUser) REFERENCES USER(idUser)," +
                "PRIMARY KEY(idCommand, idTable, idProduct, idUser)" +
                ")");
        db.execSQL("CREATE TABLE RECEIPT (" +
                "idReceipt integer NOT NULL," +
                "idCommand integer NOT NULL," +
                "hour DATETIME NOT NULL," +
                "price real," +
                "FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand)," +
                "PRIMARY KEY(idReceipt, idCommand)" +
                ")");
        db.execSQL("CREATE TABLE WAREHOUSE_CATEGORY (" +
                "idWCategory integer NOT NULL PRIMARY KEY," +
                "name text NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE WAREHOUSE_PRODUCT (" +
                "idWProduct integer NOT NULL," +
                "idWCategory integer NOT NULL," +
                "name text NOT NULL," +
                "amount integer NOT NULL," +
                "minimumAmount integer NOT NULL," +
                "FOREIGN KEY(idWCategory) REFERENCES WAREHOUSE_CATEGORY(idWCategory)," +
                "PRIMARY KEY(idWProduct, idWCategory)" +
                ")");
        db.execSQL("CREATE TABLE COMMAND_PRODUCT (" +
                "idCommand INTEGER NOT NULL," +
                "idProduct INTEGER NOT NULL," +
                "PRIMARY KEY(idCommand, idProduct)," +
                "FOREIGN KEY(idCommand) REFERENCES COMMAND(idCommand) ON DELETE CASCADE," +
                "FOREIGN KEY(idProduct) REFERENCES MENU_PRODUCT(idProduct) ON DELETE CASCADE" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Method to get the users form the database
     * @return ArrayList with all of the user objects
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USER";
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        while (res.moveToNext()) {
            list.add(new User(res.getString(res.getColumnIndex("name")), res.getInt(res.getColumnIndex("idUser")), res.getString(res.getColumnIndex("type"))));
        }
        return list;
    }

    /**
     * Method to check whether a establishment has been introduced to the database or not.
     * If it is the first time executing the app, the establishment will be registered, but just that time.
     * @return
     */
    public Boolean checkEstablishment() {
        String sql = "SELECT * FROM ESTABLISHMENT";
        Cursor res = getReadableDatabase().rawQuery(sql, null);
        return res.moveToFirst();
    }

    /**
     * Method to save the esablishment in the database
     * @param establishment Establishment object, instanced with the user's data
     */
    public void saveEstablishment(Establishment establishment) {
        ContentValues values = new ContentValues();
        values.put("name", establishment.getName());
        values.put("nif", establishment.getNif());
        values.put("email", establishment.getEmail());
        values.put("phone", establishment.getPhone());
        values.put("address", establishment.getAddress());

        getWritableDatabase().insert("ESTABLISHMENT", null, values);
    }

    /**
     * Method to save the user in the database
     * @param user user object, instanced with the app's user's input.
     */
    public void saveUser(User user){
        ContentValues values = new ContentValues();
        values.put("idUser", user.getIdEmployee());
        values.put("name", user.getName());
        values.put("type", user.getTypeEmployee());

        getWritableDatabase().insert("USER", null, values);
    }

    /**
     * Method to select a user out of their ID
     * @param iduser int, the ID of the user we want the info about
     * @return user object, instanced with the database data.
     */
    public User selectUser(int iduser){
        String sql = "SELECT * FROM USER WHERE idUser = ?";
        String[] args = new String[1];
        args[0] = iduser+"";
        Cursor res = getReadableDatabase().rawQuery(sql,args);
        res.moveToFirst();
        User user = new User(res.getString(res.getColumnIndex("name")), res.getInt(res.getColumnIndex("idUser")), res.getString(res.getColumnIndex("type")));
        return user;
    }

    /**
     * Method to select the zones of the database
     * @return arrayList of zone instances
     */
    public ArrayList<Zone> getZones() {
        String sql = "SELECT * FROM ZONE";
        Cursor res = getReadableDatabase().rawQuery(sql, null);
        ArrayList<Zone> zones = new ArrayList<>();
        while (res.moveToNext()) {
            zones.add(new Zone(res.getInt(res.getColumnIndex("idZone")), res.getString(res.getColumnIndex("name"))));
        }
        return zones;
    }

    /**
     * Method to save a zone in the database
     * @param zone zone object to be saved
     */
    public void saveZone(Zone zone){
        ContentValues values = new ContentValues();
        values.put("idZone", zone.getIdZone());
        values.put("name", zone.getName());
        getWritableDatabase().insert("ZONE", null, values);
    }

    /**
     * Method to get all the tables in a zone
     * @param idZone the ID of the zone where the tables are located
     * @return arrayList with table instances.
     */
    public ArrayList<Table> getTablesByZone(int idZone) {
        String sql = "SELECT * FROM TABLE_DB WHERE idZone = ?";
        String[] args = new String[1];
        args[0] = idZone + "";
        Cursor res = getReadableDatabase().rawQuery(sql, args);
        ArrayList<Table> tables = new ArrayList<>();
        while (res.moveToNext()) {
            tables.add(new Table(res.getInt(res.getColumnIndex("idTable")), res.getInt(res.getColumnIndex("tableCapacity"))));
        }
        return tables;
    }



}
