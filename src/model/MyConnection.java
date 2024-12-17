/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Connection;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Dell
 */
public class MyConnection {
    
    public static final String username = "root"; // MySQL Workbench username
    public static final String password = "tumotdenchin"; // MySQL password
    public static final String url = "jdbc:mysql://127.0.0.1:3308/cafe"; // Correct URL for Docker container
    public static Connection con = null;
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ""+ex, "", JOptionPane.WARNING_MESSAGE);
        }
        return con;
    }
}
