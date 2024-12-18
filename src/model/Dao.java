package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author My-Laptop
 */
public class Dao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public boolean insertProduct(Product p) {
    String sql;

    try {
        // Kiểm tra xem có hình ảnh hay không để quyết định câu SQL
        if (p.getImage() != null) {
            sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setBytes(3, p.getImage()); // Gán giá trị hình ảnh
        } else {
            sql = "INSERT INTO product (name, price) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
        }

        return ps.executeUpdate() > 0; // Kiểm tra thêm thành công
    } catch (SQLException ex) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        return false; // Trả về false nếu có lỗi xảy ra
    } finally {
        // Đảm bảo đóng PreparedStatement để giải phóng tài nguyên
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}


    public void getAllProducts(JTable table) {
        String sql = "SELECT id, name, price FROM product"; // Loại bỏ cột image nếu không cần
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public boolean update(Product product) {
        String sql = "update product set name = ?, price = ? where id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean delete(Product product) {
        try {
            ps = con.prepareStatement("delete from product where id = ?");
            ps.setInt(1, product.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void getallProducts(JTable jTable2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public int getMaxRowOrderTable () {
        int row = 0;
        try {
            st = con.createStatement () ;
            rs = st.executeQuery ("select max (cid) from cart");
            while (rs.next ()) {
                row = rs.getInt (1);
            }
        } catch (Exception ex) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row + 1;
    }
    
    public boolean isProductExist (int cid, int pid) {
        try {
            ps = con.prepareStatement ("select * from cart where cid = ? and pid = ?");
            ps.setInt (1, cid);
            ps. setInt (2, pid);
            rs = ps.executeQuery () ;
            if (rs.next ()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public int getMaxRowApaymentTable(){
        String sql = "SELECT IFNULL(MAX(pid), 0) + 1 AS next_pid FROM payment";
    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("next_pid");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return 1;
    }

    public int getMaxRowACartTable() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(cid) from cart");
            while (rs.next()) {
                row = rs.getInt(1);

            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }

    public double subTotal() {
        double subTotal = 0.0;
        int cid = getMaxRowACartTable();

        try {
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) as 'total' from cart where cid = '" + cid + "'");

            if (rs.next()) {
                subTotal = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return subTotal;
    }

    public void getProductsFromCart(JTable table) {
        int cid = getMaxRowACartTable();
        String sql = "select * from cart where cid = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;

            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getDouble(6);
                model.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getMaxRowAOrderTable() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(oid) from `order`");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        return row + 1;
    }
        
       
    public boolean insertCart(int cid, int pid, String pName, int qty, double price, double total) {
    String sql = "INSERT INTO cart (cid, pid, pName, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, cid);
        ps.setInt(2, pid);
        ps.setString(3, pName);
        ps.setInt(4, qty);
        ps.setDouble(5, price);
        ps.setDouble(6, total);

        return ps.executeUpdate() > 0; // Trả về true nếu insert thành công
    } catch (SQLException e) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
    }
    return false;
}


    private PreparedStatement setInt(int i, int pid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean insertPayment (Payment payment) {
        String sql = "INSERT INTO payment (pid, cName, proid, pName, total, pdate) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, payment.getPid());
            ps.setString(2, payment.getcName());
            ps.setString(3, payment.getProId());
            ps.setString(4, payment.getProName());
            ps.setDouble(5, payment.getTotal());
            ps.setString(6, payment.getDate());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
        ex.printStackTrace(); // In lỗi ra console
    }
    return false;
    }
    
    public boolean deleteCart (int cid) {
        try {
            ps = con.prepareStatement ("delete from cart where cid = ?");
            ps.setInt (1, cid);
            return ps.executeUpdate () > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public void getPaymentDetails(JTable table) {
        String sql = "select * from payment order by pid desc";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;

            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                model.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int totalProducts(){
        int total = 0;

      try {
        st = con.createStatement();
        rs = st.executeQuery("select count(*) as 'total' from product");
        if(rs.next()){
            total = (int) rs.getDouble(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public double todayRevenue(){
        double total = 0.0;

      try {
        st = con.createStatement();
            String date = null;
        rs = st.executeQuery("select sum(total) as 'total' from payment where pdate = '"+ date +"'");
        if(rs.next()){
            total = rs.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public double totalRevenue(){
        double total = 0.0;

      try {
        st = con.createStatement();
        rs = st.executeQuery("select sum(total) as 'total' from payment");
        if(rs.next()){
            total = rs.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    public boolean moveToStatistics(int totalQty, double totalAmount, String date) {
        String sql = "INSERT INTO statistics (qty, total, date) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, totalQty);
            ps.setDouble(2, totalAmount);
            ps.setString(3, date);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
           }
    }
    public void clearCartTable() {
        String sql = "DELETE FROM cart";
        try {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}


