package com.example.barlink.products;


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

    public Category(int idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
        this.expanded = false;
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
