/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import model.MyConnection;
import cafe.ForgotPasswordFrame;

/**
 *
 * @author Dell
 */

public class AdminDao {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    public int getMaxRowAdminTable() {
        int maxRow = 0;
    String sql = "SELECT MAX(id) FROM admin"; // Truy vấn lấy ID lớn nhất
    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            maxRow = rs.getInt(1); // Lấy giá trị ID lớn nhất
        }
    } catch (Exception e) {
        Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, e);
    }
    return maxRow + 1;
    }

    public boolean isAdminNameExist (String username){
        try {
            ps = con.prepareStatement("select * from admin where xusername = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
  public boolean insert(Admin admin) {
    String sql = "INSERT INTO admin (username, password, s_ques, ans) VALUES (?, ?, ?, ?)";
    try {
        ps = con.prepareStatement(sql);
        
        ps.setString(1, admin.getUsername());
        ps.setString(2, admin.getPassword());
        ps.setString(3, admin.getsQues());
        ps.setString(4, admin.getAns());

        return ps.executeUpdate() > 0; // Trả về true nếu insert thành công
    } catch (Exception ex) {
        Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}


    public boolean login(String username, String password){
        try {
            ps = con.prepareStatement("select * from admin where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return false;
    }

    public boolean getSecurity(String username){
        try {
            ps = con.prepareStatement("select * from admin where username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                ForgotPasswordFrame.jTextField7.setText(rs.getString(4));
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return false;
    }

    public boolean getAns(String username, String newAns){
        try {
            ps = con.prepareStatement("select * from admin where username = ? ");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(5);
                if (newAns.equals(oldAns)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return false;
    }
    public boolean setPassword(String username, String password) {
        String sql = "update admin set password = ? where username = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }
}
