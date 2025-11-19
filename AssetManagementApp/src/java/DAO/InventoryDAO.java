/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utilities.DBConnection;
import Model.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ranseeeee
 */
public class InventoryDAO {
    private Connection conn;
    
    public InventoryDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }
    
    // Create Inventory Record
    public boolean insertInventory(Inventory inventory) {
        String sql = "INSERT INTO Inventory (itemName, itemType, unit, stockQty, reorderLevel, i_status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, inventory.getItemName());
            stm.setString(2, inventory.getItemType());
            stm.setString(3, inventory.getUnit());
            stm.setInt(4, inventory.getStockQty());
            stm.setInt(5, inventory.getReorderLevel());
            stm.setString(6, "active");
            
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Read Inventory Record by ID
    public Inventory getInventoryID(int itemID) {
        String sql = "SELECT * FROM Inventory WHERE itemID = ?";
        
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, itemID);
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                Inventory inventory = new Inventory();
                
                inventory.setItemID(rs.getInt("itemID"));
                inventory.setItemName(rs.getString("itemName"));
                inventory.setItemType(rs.getString("itemType"));
                inventory.setUnit(rs.getString("unit"));
                inventory.setStockQty(rs.getInt("stockQty"));
                inventory.setReorderLevel(rs.getInt("reorderLevel"));
                inventory.setI_status(rs.getString("i_status"));
                
                return inventory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Read All Inventory Records
    public List<Inventory> getAllInventory() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";
        
        try (Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery(sql);
            
            while (rs.next()) {
                Inventory inventory = new Inventory();
                
                inventory.setItemID(rs.getInt("itemID"));
                inventory.setItemName(rs.getString("itemName"));
                inventory.setItemType(rs.getString("itemType"));
                inventory.setUnit(rs.getString("unit"));
                inventory.setStockQty(rs.getInt("stockQty"));
                inventory.setReorderLevel(rs.getInt("reorderLevel"));
                inventory.setI_status(rs.getString("i_status"));
                
                list.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    // Update Inventory Record
    public boolean updateInventory(Inventory inventory) {
        String sql = "UPDATE Inventory SET itemName=?, itemType=?, unit=?, stockQty=?, reorderLevel=?, i_status=? WHERE itemID=?";
        
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, inventory.getItemName());
            stm.setString(2, inventory.getItemType());
            stm.setString(3, inventory.getUnit());
            stm.setInt(4, inventory.getStockQty());
            stm.setInt(5, inventory.getReorderLevel());
            stm.setString(6, inventory.getI_status());
            stm.setInt(7, inventory.getItemID());
            
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
