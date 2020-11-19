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

public class DBManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BarLink.db";
    private static DBManager sInstance;

    public static synchronized DBManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    public Boolean checkEstablishment() {
        String sql = "SELECT * FROM ESTABLISHMENT";
        Cursor res = getReadableDatabase().rawQuery(sql, null);
        return res.moveToFirst();
    }

    public void saveEstablishment(Establishment establishment) {
        ContentValues values = new ContentValues();
        values.put("name", establishment.getName());
        values.put("nif", establishment.getNif());
        values.put("email", establishment.getEmail());
        values.put("phone", establishment.getPhone());
        values.put("address", establishment.getAddress());

        getWritableDatabase().insert("ESTABLISHMENT", null, values);
    }

    public void saveUser(User user){
        ContentValues values = new ContentValues();
        values.put("idUser", user.getIdEmployee());
        values.put("name", user.getName());
        values.put("type", user.getTypeEmployee());

        getWritableDatabase().insert("USER", null, values);
    }

    public User selectUser(int iduser){
        String sql = "SELECT * FROM USER WHERE idUser = ?";
        String[] args = new String[1];
        args[0] = iduser+"";
        Cursor res = getReadableDatabase().rawQuery(sql,args);
        res.moveToFirst();
        User user = new User(res.getString(res.getColumnIndex("name")), res.getInt(res.getColumnIndex("idUser")), res.getString(res.getColumnIndex("type")));
        return user;
    }

    public ArrayList<Zone> getZones() {
        String sql = "SELECT * FROM ZONE";
        Cursor res = getReadableDatabase().rawQuery(sql, null);
        ArrayList<Zone> zones = new ArrayList<>();
        while (res.moveToNext()) {
            zones.add(new Zone(res.getInt(res.getColumnIndex("idZone")), res.getString(res.getColumnIndex("name"))));
        }
        return zones;
    }

    public void saveZone(Zone zone){
        ContentValues values = new ContentValues();
        values.put("idZone", zone.getIdZone());
        values.put("name", zone.getName());
        getWritableDatabase().insert("ZONE", null, values);
    }
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
