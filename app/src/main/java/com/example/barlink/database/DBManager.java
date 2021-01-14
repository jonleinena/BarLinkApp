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
import com.example.barlink.products.Category;
import com.example.barlink.products.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class to manage all the access to the database
 * @author github.com/jonleinena
 * @author github.com/FerreMikel
 */
public class DBManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BarLink.db";
    private static DBManager sInstance;

    /**
     * Static method to get the instance of the class that has been previously created, to avoid creating a new one.
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
                "name TEXT,"+
                "capacity integer"+
                ")");
        db.execSQL("CREATE TABLE TABLE_DB (" +
                "idTable integer NOT NULL PRIMARY KEY," +
                "idZone integer NOT NULL," +
                "FOREIGN KEY(idZone) REFERENCES ZONE(idZone)" +
                ")");
        db.execSQL("CREATE TABLE PRODUCT_CATEGORY (" +
                "idCategory integer PRIMARY KEY," +
                "name text NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE MENU_PRODUCT (" +
                "idProduct integer," +
                "idCategory integer NOT NULL," +
                "name text NOT NULL," +
                "price real NOT NULL," +
                "imagePath text NOT NULL," +
                "FOREIGN KEY(idCategory) REFERENCES PRODUCT_CATEGORY(idCategory)," +
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
        db.execSQL("INSERT INTO PRODUCT_CATEGORY(idCategory, name)" +
                "VALUES (1, 'Bebidas')," +
                "(2, 'Entrantes')," +
                "(3, 'Carnes')," +
                "(4, 'Pescados')," +
                "(5, 'Ensaladas')," +
                "(6, 'Postres')");
        db.execSQL("INSERT INTO MENU_PRODUCT(idProduct, idCategory, name, imagePath, price)" +
                "VALUES (1, 1, 'Agua', 'bebida.png', 1.5)," +
                " (2, 1, 'Fanta de Naranja', 'bebida.png', 2)," +
                " (3, 1, 'Fanta de Limón', 'bebida.png', 2)," +
                " (4, 1, 'Coca Cola', 'bebida.png', 2.2)," +
                " (5, 1, 'Cerveza', 'bebida.png', 1.8)," +
                " (6, 2, 'Jamón Ibérico', 'entrantes.png', 15)," +
                " (7, 2, 'Croquetas', 'entrantes.png', 10)," +
                " (8, 2, 'Fritos', 'entrantes.png', 11)," +
                " (9, 2, 'Caldo de pescado', 'entrantes.png', 6.5)," +
                " (10, 3, 'Entrecot', 'carne.png', 12)," +
                " (11, 3, 'Escalope', 'carne.png', 11)," +
                " (12, 3, 'Chuleta', 'carne.png', 27.5)," +
                " (13, 4, 'Lubina', 'pez.png', 15)," +
                " (14, 4, 'Merluza', 'pez.png', 19)," +
                " (15, 4, 'Rodaballo', 'pez.png', 28)," +
                " (16, 4, 'Bacalao', 'pez.png', 16)," +
                " (17, 5, 'Ensalada mixta', 'ensalada.png', 7)," +
                " (18, 5, 'Ensalada César', 'ensalada.png', 9.5)," +
                " (19, 5, 'Ensalada de gulas', 'ensalada.png', 10.3)," +
                " (20, 6, 'Coulant de chocolate', 'postre.png', 4.5)," +
                " (21, 6, 'Helado', 'postre.png', 1.5)," +
                " (22, 6, 'Tarta de queso', 'postre.png', 3.5)," +
                " (23, 6, 'Café', 'postre.png', 1.1)," +
                " (24, 6, 'Flan de huevo', 'postre.png', 1.5)," +
                " (25, 6, 'Combinado', 'postre.png', 5.5)");
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
            zones.add(new Zone(res.getInt(res.getColumnIndex("idZone")), res.getString(res.getColumnIndex("name")), res.getInt(res.getColumnIndex("capacity"))));
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
        values.put("capacity", zone.getCapacity());
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
            tables.add(new Table(res.getInt(res.getColumnIndex("idTable")), res.getInt(res.getColumnIndex("idZone"))));
        }
        return tables;
    }

    /**
     * Method to save tables in database
     * @param table table element to save
     */
    public void saveTables(Table table){
        ContentValues values = new ContentValues();
        values.put("idTable", table.getIdTable());
        values.put("idZone", table.getIdZone());
        getWritableDatabase().insert("TABLE_DB", null, values);

    }

    /**
     * Method to get all the product categories from the database
     * @return list containing all categories
     */
    public ArrayList<Category> getCategories() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT_CATEGORY";
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        while (res.moveToNext()) {
            list.add(new Category(res.getInt(res.getColumnIndex("idCategory")), res.getString(res.getColumnIndex("name"))));
        }
        return list;
    }

    /**
     * Method to get all products from a determined category
     * @param cat category for the identifier
     * @return list containing all product objects
     */
    public ArrayList<Product> getProducts(Category cat){
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM MENU_PRODUCT WHERE idCategory = ?";
        String[] args = new String[1];
        args[0] = cat.getIdCategory() + "";
        Cursor res = getReadableDatabase().rawQuery(sql, args);
        while (res.moveToNext()) {
            products.add(new Product(res.getInt(res.getColumnIndex("idProduct")), res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("imagePath")), res.getFloat(res.getColumnIndex("price"))));
        }
        cat.setProducts(products);
        return products;
    }

}
