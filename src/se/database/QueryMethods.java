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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Ringblom
 */
public class QueryMethods {
    
    Connection con;
    String query = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    

    
    public void insertGuest(String firstName, String lastName, String socialNumber, String password){
        
        query = "INSERT INTO guest (first_name, last_name, person_id, password) VALUES (?,?,?,?)";
        
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            
            ps.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
