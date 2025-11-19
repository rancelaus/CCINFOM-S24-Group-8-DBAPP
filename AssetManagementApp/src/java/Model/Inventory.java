/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Rance Laus
 */
public class Inventory {
    // Attributes
    public int itemID;
    public String itemName;
    public String itemType;
    public String unit;
    public int stockQty;
    public int reorderLevel;
    public String i_status;
    
    public Inventory() {}
    
    public Inventory(int itemID, String itemName, String itemType, String unit, int stockQty, int reorderLevel, String i_status) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
        this.unit = unit;
        this.stockQty = stockQty;
        this.reorderLevel = reorderLevel;
        this.i_status = i_status;
    }
    
    // Getters and Setters
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getI_status() {
        return i_status;
    }

    public void setI_status(String i_status) {
        this.i_status = i_status;
    }
}