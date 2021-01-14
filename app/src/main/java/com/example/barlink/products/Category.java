package com.example.barlink.products;


import java.util.ArrayList;

/**
 * Represents a category of product
 *
 * @author Jon Leiñena  - https://github.com/jonleinena
 * @author Mikel Ferrer - https://github.com/FerreMikel
 */
public class Category {
    private int idCategory;
    private String name;
    private boolean expanded;
    private ArrayList<Product> products;


    public Category(int idCategory, String name ) {
        this.idCategory = idCategory;
        this.name = name;
        this.expanded = false;
        this.products = new ArrayList<Product>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
