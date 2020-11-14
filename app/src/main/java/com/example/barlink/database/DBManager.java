package com.example.barlink.database;


import com.example.barlink.command.User;
import com.example.barlink.establishment.Establishment;
import com.example.barlink.products.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;

/**
 * @author github.com/jonleinena
 * @author github.com/FerreMikel
 */

public class DBManager extends SQLiteAssetHelper {

    public static String DATABASE_NAME = "BarLinkPrueba.db";
    private static final int DATABASE_VERSION = 1;

    public static Connection conn = null;
    public static String url = "jdbc:sqldroid:/data/data/com.example.barlink/databases/BarLinkPrueba.db";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Static method to start the connection to the database
     * @param context
     */
    public static void startConnection(Context context) {
        try {
            new DBManager(context).getReadableDatabase();
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
            conn = DriverManager.getConnection(url);
            Log.w("myApp", "Conexion satisfactoria");

        } catch (SQLException | ClassNotFoundException e) {
            Log.w("SportTogether", e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to end the connection to the database
     */
    public static void endConnection(){
        try {
            conn.close();
            System.out.println("Connection to SQLite has been ended.");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to insert establihsment instance into database. Only executed the first time the programme is run.
     * @param est instance of etablishment.
     */
    public static void InsertEstablishment(Establishment est){
        String sql = "INSERT INTO ESTABLISHMENT(NAME, NIF, EMAIL, TELEPHONE, ADRESS) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, est.getName());
            pstmt.setString(2, est.getNif());
            pstmt.setString(3, est.getEmail());
            pstmt.setInt(4, est.getPhone());
            pstmt.setString(5, est.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Method to check whether there is any establishment registered.
     * @return whether there is any estblishment
     */
    public static boolean checkEstablishment(){
        String sql = "SELECT * FROM ESTABLISHMENT";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.isBeforeFirst()) {
                return true;
            }return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }



    }

    /**
     * Method to insert products into the database, getting the data from the product instance
     * @param p product instance
     * @param description product description
     */
    public static void insertProduct(Product p, String description){
        String sql = "INSERT INTO MENU_PRODUCT(idProduct, idCategory, name, price, description, imagepath) VALUES(?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getIdProduct());
            pstmt.setInt(2, p.getIdProduct());
            pstmt.setString(3, p.getName());
            pstmt.setFloat(4, p.getPrice());
            pstmt.setString(5, description);
            pstmt.setString(6, p.getImg());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Static method to select all the user from the database
     * @return ArrayList with instances of Users, created with data from the database
     */
    public static ArrayList<User> readUsers(){
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USER";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                list.add(new User(rs.getString("name"),rs.getInt("idUser"), rs.getString("type")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


}