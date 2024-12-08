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

/**
 *
 * @author Dell
 */
public class AdminDao{
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    public int getMaxRowAdminTable() {
        int row = 0;
        
        st = con.createStatement();
        rs = st.executeQuery("select max(id) from admin");
        
        whilw(rs.next())
        {
            row = rs.getInt(1);
        }
    } catch (SQLEXception ex) {
    Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);

    }
    return row + 1;
    }

    
}
