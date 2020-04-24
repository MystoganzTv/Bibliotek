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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.model.Admin;
import se.model.Books;
import se.model.E_Books;
import se.model.Guest;
import se.model.Librarian;

/**
 *
 * @author Erik Ringblom
 */
public class QueryMethods {
    
    static Connection con = null;
    String query = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public QueryMethods() {
      
    }

    
public void insertEmail(String email){
    con = MyConnection.getConnection();
    
    query = "INSERT INTO emails (email) VALUES (?)";
    try {
        ps = con.prepareStatement(query);
        ps.setString(1,email);
        ps.execute();
    }catch(SQLException e){
        
    }finally{
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public boolean isEmailTaken(String email){
    
    con = MyConnection.getConnection();
    boolean check = false;
   
    try {
        Statement statement = con.createStatement();
        System.out.println(email);
        query = "SELECT email FROM emails WHERE email=" + "\"" + email +"\"";
        rs = statement.executeQuery(query);
        
       while(rs.next()){
        if(rs.getString(1).equals(email)){
            check = true;
        }else {
            check = false;
        }
       }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }finally{
        try {
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return check;
}
    
    public int insertGuest(String firstName, String lastName, String socialNumber, String password, String email){
        int idGuest = 0;
        
        insertEmail(email);
        con = MyConnection.getConnection();
                
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
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idGuest;

    }
   
    
     public int insertAdmin(String firstName, String lastName, String socialNumber, String password, String email){
        
        insertEmail(email);
       int idAdmin = 0;
       con = MyConnection.getConnection();
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
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idAdmin;

    }
   
     
     public int insertLibrarian(String firstName, String lastName, String socialNumber, String password, String email){
       
         insertEmail(email);
        int idLibrarian = 0;
        con = MyConnection.getConnection();
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
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idLibrarian;

    }
     
    public ArrayList<Admin> findAdmins() {
        
        try
        {
            MyConnection tryConnect = new MyConnection();
            ArrayList<Admin> admins = new ArrayList<Admin>();
            Admin currentAdmin;
            
            Connection conn = tryConnect.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM admins");

            ResultSet results = stmt.getResultSet();
            while(results.next())
            {
                currentAdmin = new Admin(results.getString("first_name"),
                                         results.getString("last_name"),
                                         results.getString("person_id"),
                                         results.getString("password"),
                                         Integer.parseInt(results.getString("id")),
                                         results.getString("email"));
                admins.add(currentAdmin);
                currentAdmin = null;
            }
            
            conn.close();
            stmt.close();
            
            return admins;
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong: " + e);
        }
        
        return null;
    }
    
    public ArrayList<Librarian> findLibrarians() {
        
        try
        {
           
            ArrayList<Librarian> librarians = new ArrayList<Librarian>();
            Librarian currentLibrarian;
            
             con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM librarians");

            ResultSet results = stmt.getResultSet();
            while(results.next())
            {
                currentLibrarian = new Librarian(Integer.parseInt(results.getString("id")),
                                                 results.getString("first_name"),
                                                 results.getString("last_name"),
                                                 results.getString("person_id"),
                                                 results.getString("password"),
                                                 results.getString("email"));
                librarians.add(currentLibrarian);
                currentLibrarian = null;
            }
            
            con.close();
            stmt.close();
            
            return librarians;
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong: " + e);
        }
        
        return null;
    }
    
    public ArrayList<Guest> findGuests() {
        
        try
        {
            
            ArrayList<Guest> guests = new ArrayList<Guest>();
            Guest currentGuest;
            
            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM guests");

            ResultSet results = stmt.getResultSet();
            while(results.next())
            {
                currentGuest = new Guest(Integer.parseInt(results.getString("id")),
                                                 results.getString("first_name"),
                                                 results.getString("last_name"),
                                                 results.getString("person_id"),
                                                 results.getString("password"),
                                                 results.getString("email"));
                guests.add(currentGuest);
                currentGuest = null;
            }
            
            con.close();
            stmt.close();
            
            return guests;
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
       
    public static void addBook(Books b, int category) {
        
        MyConnection tryConnect = new MyConnection();
        con = MyConnection.getConnection();
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO books(title, author, isbn, publisher, purchase_price, books_kategori_id)" + 
                         " VALUES ('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                         + b.getPublisher() + "', " + b.getPurchase_price() + ", " + category + ")");
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong while trying to add a book: " + e.getMessage());
        }
    }
    
    public static void addEBook(E_Books b, int category) {
        
        MyConnection tryConnect = new MyConnection();
        con = MyConnection.getConnection();
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO e_books(title, author, isbn, publisher, purchase_price, ebooks_kategori_id) "
                         + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                         + b.getPublisher() + "', " + b.getPurchase_price() + ", " + category + ")");
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong while trying to add a book: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        E_Books b = new E_Books(2, "Tarzan of the Apes", "Edgar Rice Burroughs", "9789871596898", "A. C. McClurg", 223);
        addEBook(b, 2);
    }
}
