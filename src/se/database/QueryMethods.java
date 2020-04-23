/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Erik Ringblom
 */
public class QueryMethods {
    
    Connection con = null;
    String query = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public QueryMethods() {
        con = MyConnection.getConnection();
    }

    

    
    public int insertGuest(String firstName, String lastName, String socialNumber, String password, String email){
        int idGuest = 0;
        query = "INSERT INTO guests (first_name, last_name, person_id, password, email) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);
            
            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGuest = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

      } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idGuest;

    }
   
    
     public int insertAdmin(String firstName, String lastName, String socialNumber, String password, String email){
        
       int idAdmin = 0;
        query = "INSERT INTO admins (first_name, last_name, person_id, password, email) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);
            
            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idAdmin = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

      } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idAdmin;

    }
   
     
     public int insertLibrarian(String firstName, String lastName, String socialNumber, String password, String email){
       
        int idLibrarian = 0;
        query = "INSERT INTO librarians (first_name, last_name, person_id, password) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);
            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idLibrarian = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

      } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idLibrarian;

    }
     
    public DefaultTableModel displayAdmins(String[] colNames) {
        
        try
        {
            DefaultTableModel model = new DefaultTableModel(colNames, 0);
            
            MyConnection tryConnect = new MyConnection();
            
            Connection conn = tryConnect.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM admins");

            ResultSet results = stmt.getResultSet();
            
            while(results.next())
            {
                model.addRow(new Object[] { results.getString("first_name"), results.getString("last_name"), results.getString("person_id"),
                                            results.getString("password"), results.getString("email")});
            }
            
            conn.close();
            stmt.close();
            
            return model;
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong: " + e);
        }
        
        return null;
    }
    
    public DefaultTableModel displayLibrarians(String[] colNames) {
        
        try
        {
            DefaultTableModel model = new DefaultTableModel(colNames, 0);
            
            MyConnection tryConnect = new MyConnection();
            
            Connection conn = tryConnect.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM librarians");

            ResultSet results = stmt.getResultSet();
            
            while(results.next())
            {
                model.addRow(new Object[] { results.getString("first_name"), results.getString("last_name"), results.getString("person_id"),
                                            results.getString("password"), results.getString("email")});
            }
            
            conn.close();
            stmt.close();
            
            return model;
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong: " + e);
        }
        
        return null;
    }
    
    public DefaultTableModel displayGuests(String[] colNames) {
        
        try
        {
            DefaultTableModel model = new DefaultTableModel(colNames, 0);
            
            MyConnection tryConnect = new MyConnection();
            
            Connection conn = tryConnect.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM guests");

            ResultSet results = stmt.getResultSet();
            
            while(results.next())
            {
                model.addRow(new Object[] { results.getString("first_name"), results.getString("last_name"), results.getString("person_id"),
                                            results.getString("password"), results.getString("email")});
            }
            
            conn.close();
            stmt.close();
            
            return model;
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong: " + e);
        }
        
        return null;
    }
    
       public String loginChecker(String user, String username, String password) {
        String exist = " select email, password from "+ user + " where email = '"+username +"'"
                + "and password = '"+password+"';" ; 
        PreparedStatement check ;
        String email = "";
        
        try{
        check = MyConnection.getConnection().prepareStatement(exist);
        
        
        ResultSet rs = check.executeQuery();
        if (rs.next()){
        email = rs.getString("email");
        } 
        }catch(Exception e){
        System.out.println(e.toString() + " loginChecker()");
        }
        return email;
        
            }
}
