/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;
import java.util.*;
import java.sql.*;


/**
 *
 * @author Administrator
 */
public class AssetDAO {
    
    public AssetDAO(){}
    
    public int assetID;
    public char assetType;
    public java.sql.Date purchaseDate;
    public java.sql.Date expiryDate;
    public String a_status;
    
    public ArrayList<Integer> assetIDlist = new ArrayList<>();
    public ArrayList<Character> assetTypelist = new ArrayList<>();
    public ArrayList<java.sql.Date> purchaseDatelist = new ArrayList<>();
    public ArrayList<java.sql.Date> expiryDatelist = new ArrayList<>();
    public ArrayList<String> a_statuslist = new ArrayList<>();
    
    
    public boolean create_asset(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asset_management_db", "root", "12345678");
            
            
            String query = "INSERT INTO Asset(expiryDate, purchaseDate, a_status, assetType) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstm.setDate(1, expiryDate);
            pstm.setDate(2, purchaseDate);
            a_status = "active";
            pstm.setString(3, a_status);
            pstm.setString(4, String.valueOf(assetType));
            pstm.executeUpdate();
            
            pstm.close();
            conn.close();
            return true;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public List<AssetDAO> read_asset(){
       List<AssetDAO> list = new ArrayList<>();
       
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn;
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asset_management_db", "root", "12345678");
           
           String query = "SELECT assetID, expiryDate, purchaseDate, a_status, assetType FROM asset ORDER BY assetID";
           PreparedStatement pstm = conn.prepareStatement(query);
           ResultSet result = pstm.executeQuery();
           
           while(result.next()){
               AssetDAO A = new AssetDAO();
               A.assetID = result.getInt("assetID");
               A.expiryDate = result.getDate("expiryDate");
               A.purchaseDate = result.getDate("purchaseDate");
               A.a_status = result.getString("a_status");
               A.assetType = result.getString("assetType").charAt(0);
               list.add(A);
           }
           
           result.close();
           pstm.close();
           conn.close();
       } catch (Exception e){
           e.printStackTrace();
           System.out.println("READ ERROR: " + e.toString());
       }
       return list;
   }
    
    public static void main (String args[]){
        AssetDAO A = new AssetDAO();
        A.create_asset();
    }
    
    public AssetDAO getAssetById(int id) {
    AssetDAO A = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/asset_management_db",
            "root", "12345678"
        );

        String q = "SELECT * FROM asset WHERE assetID = ?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            A = new AssetDAO();
            A.assetID = rs.getInt("assetID");
            A.expiryDate = rs.getDate("expiryDate");
            A.purchaseDate = rs.getDate("purchaseDate");
            A.a_status = rs.getString("a_status");
            A.assetType = rs.getString("assetType").charAt(0);
        }

        rs.close(); ps.close(); conn.close();
    } catch (Exception e) { e.printStackTrace(); }

    return A;
    }
    
    public boolean updateAssetOptional(int id, java.sql.Date expiry, java.sql.Date purchase, String status) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/asset_management_db",
            "root", "12345678"
        );

        // Build dynamic SQL
        StringBuilder sb = new StringBuilder("UPDATE asset SET ");
        ArrayList<Object> params = new ArrayList<>();

        if (expiry != null) {
            sb.append("expiryDate=?, ");
            params.add(expiry);
        }
        if (purchase != null) {
            sb.append("purchaseDate=?, ");
            params.add(purchase);
        }
        if (status != null) {
            sb.append("a_status=?, ");
            params.add(status);
        }

        // No changes
        if (params.isEmpty()) return true;

        // Remove last comma+space
        sb.setLength(sb.length() - 2);

        sb.append(" WHERE assetID=?");
        params.add(id);

        PreparedStatement ps = conn.prepareStatement(sb.toString());

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ps.executeUpdate();
        ps.close(); conn.close();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
}
