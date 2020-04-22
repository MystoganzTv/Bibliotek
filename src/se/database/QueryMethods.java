/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
   
}
