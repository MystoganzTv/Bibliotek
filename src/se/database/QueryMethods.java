/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.model.Admin;
import se.model.Booking;
import se.model.Books;
import se.model.BorrowEBooks;
import se.model.BorrowedBooks;
import se.model.Category;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.Guest;
import se.model.Librarian;
import se.model.LibraryCards;
import se.model.Seminar;

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

    }
    public boolean isPersonNumberTaken(String personNumber){
        String adminsIds = "SELECT person_id FROM admins";
        String librariansIds = "SELECT person_id FROM librarians";
        String guestsIds = "SELECT person_id FROM guests";
        con = MyConnection.getConnection();
        ArrayList<String> personIDs = new ArrayList<>();
        try{
         Statement statement = con.createStatement();
         rs = statement.executeQuery(adminsIds);
         
         while(rs.next()){
             
         String id = rs.getString(1);
         personIDs.add(id);
         }
         
         rs = statement.executeQuery(librariansIds);
         while(rs.next()){
             String id = rs.getString(1);
             personIDs.add(id);
         }
         
         rs = statement.executeQuery(guestsIds);
         
         while(rs.next()){
             String id = rs.getString(1);
             personIDs.add(id);
         }
        }catch(SQLException e){
            
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return personIDs.contains(personNumber);
    }

    public void insertEmail(String email) {
        con = MyConnection.getConnection();

        query = "INSERT INTO emails (email) VALUES (?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.execute();
        } catch (SQLException e) {

        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isEmailTaken(String email) {

        con = MyConnection.getConnection();
        boolean check = false;

        try {
            Statement statement = con.createStatement();
            System.out.println(email);
            query = "SELECT email FROM emails WHERE email=" + "\"" + email + "\"";
            rs = statement.executeQuery(query);

            while (rs.next()) {
                if (rs.getString(1).equals(email)) {
                    check = true;
                } else {
                    check = false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public int insertGuest(String firstName, String lastName, String socialNumber, String password, String email) {
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
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idGuest;

    }

    public int insertAdmin(String firstName, String lastName, String socialNumber, String password, String email) {

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
            
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idAdmin;

    }

    public int insertLibrarian(String firstName, String lastName, String socialNumber, String password, String email) {

        insertEmail(email);
        int idLibrarian = 0;
        con = MyConnection.getConnection();
        query = "INSERT INTO librarians (first_name, last_name, person_id, password, email) VALUES (?,?,?,?,?)";

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
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idLibrarian;

    }

    public ArrayList<DeletedBook> findDeletedBooks() {

        try {
            MyConnection tryConnect = new MyConnection();
            ArrayList<DeletedBook> deletedBooks = new ArrayList<DeletedBook>();
            DeletedBook currentDeletedBooks;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();

            stmt.executeQuery("SELECT * FROM deleted_books WHERE bookType = 'Book'");
            ResultSet results = stmt.getResultSet();

            while (results.next()) {

                currentDeletedBooks = new DeletedBook(Integer.parseInt(results.getString("id")),
                        results.getString("title"),
                        results.getString("author"),
                        results.getString("publisher"),
                        results.getString("isbn"),
                        results.getDouble("purchase_price"),
                        results.getString("bookType"),
                        results.getString("category"),
                        results.getString("placement"),
                        results.getString("notes"));
                deletedBooks.add(currentDeletedBooks);
                currentDeletedBooks = null;
            }

            
            stmt.close();

            return deletedBooks;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }finally {

            try {
                con.close();                
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ArrayList<DeletedBook> findDeletedEBooks() {

        try {
            MyConnection tryConnect = new MyConnection();
            ArrayList<DeletedBook> deletedBooks = new ArrayList<DeletedBook>();
            DeletedBook currentDeletedBooks;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            //     stmt.execute("SELECT * FROM deleted_books");

            stmt.executeQuery("SELECT * FROM deleted_books WHERE bookType = 'EBook'");
            ResultSet results = stmt.getResultSet();

            while (results.next()) {

                currentDeletedBooks = new DeletedBook(Integer.parseInt(results.getString("id")),
                        results.getString("title"),
                        results.getString("author"),
                        results.getString("publisher"),
                        results.getString("isbn"),
                        results.getDouble("purchase_price"),
                        results.getString("bookType"),
                        results.getString("category"),
                        results.getString("placement"),
                        results.getString("notes"));
                deletedBooks.add(currentDeletedBooks);
                currentDeletedBooks = null;
            }            
            stmt.close();

            return deletedBooks;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }finally {

            try {
                con.close();                
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ArrayList<Admin> findAdmins() {

        try {
            MyConnection tryConnect = new MyConnection();
            ArrayList<Admin> admins = new ArrayList<Admin>();
            Admin currentAdmin;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM admins");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentAdmin = new Admin(results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        Integer.parseInt(results.getString("id")),
                        results.getString("email"));
                admins.add(currentAdmin);
                currentAdmin = null;
            }
            
            stmt.close();

            return admins;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }finally {

            try {
                con.close();                
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public ArrayList<Librarian> findLibrarians() {

        try {

            ArrayList<Librarian> librarians = new ArrayList<Librarian>();
            Librarian currentLibrarian;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM librarians");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentLibrarian = new Librarian(Integer.parseInt(results.getString("id")),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        results.getString("email"));
                librarians.add(currentLibrarian);
                currentLibrarian = null;
            }            
            stmt.close();

            return librarians;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }finally {

            try {
                con.close();                
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public Guest findGuestByMail(String email) {
        Guest g = new Guest();

        try {

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM guests WHERE email = '" + email + "'");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                g = new Guest(Integer.parseInt(results.getString("id")),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        results.getString("email"));
            }

            con.close();
            stmt.close();

            return g;
        } catch (SQLException e) {
            System.out.println("Something went wrong in findGuestByMail(): " + e);
        }

        return null;
    }

    public ArrayList<Guest> findGuests() {

        try {

            ArrayList<Guest> guests = new ArrayList<Guest>();
            Guest currentGuest;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM guests");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentGuest = new Guest(Integer.parseInt(results.getString("id")),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        results.getString("email"));
                guests.add(currentGuest);
                currentGuest = null;
            }

            results.close();
            stmt.close();

            return guests;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public ArrayList<Books> findBooks() {

        try {

            ArrayList<Books> books = new ArrayList<Books>();
            Books currentBooks;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM books");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentBooks = new Books(Integer.parseInt(results.getString("id")),
                        results.getString("title"),
                        results.getString("author"),
                        results.getString("isbn"),
                        results.getString("publisher"),
                        results.getDouble("purchase_price"),
                        results.getString("category"),
                        results.getString("placement"),
                        results.getString("description"),
                        results.getDate(10));
                books.add(currentBooks);
                currentBooks = null;
            }

            results.close();
            stmt.close();

            return books;
        } catch (SQLException e) {
            System.out.println("Något gick fel: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public ArrayList<E_Books> findEBooks() {

        try {

            ArrayList<E_Books> eBooks = new ArrayList<E_Books>();
            E_Books currentEBooks;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM e_books");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentEBooks = new E_Books(Integer.parseInt(results.getString("id")),
                        results.getString("title"),
                        results.getString("author"),
                        results.getString("isbn"),
                        results.getString("publisher"),
                        results.getDouble("purchase_price"),
                        results.getString("category"),
                        results.getString("description"),
                        results.getDate("date"));
                eBooks.add(currentEBooks);
                currentEBooks = null;
            }

            results.close();
            stmt.close();

            return eBooks;
        } catch (SQLException e) {
            System.out.println("Något gick fel: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public ArrayList<Category> findCategories() {

        try {

            ArrayList<Category> categories = new ArrayList<Category>();
            Category currentCat;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM categories");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentCat = new Category(results.getString("category"));
                categories.add(currentCat);
                currentCat = null;
            }

            stmt.close();
            results.close();

            return categories;
        } catch (SQLException e) {
            System.out.println("Något gick fel: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public String loginChecker(String user, String username, String password) {
        String exist = " select email, password from " + user + " where email = '" + username + "'"
                + "and password = '" + password + "';";
        con = MyConnection.getConnection();
        PreparedStatement check;
        String email = "";

        try {
            check = con.prepareStatement(exist);

            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }

            rs.close();
            check.close();
        } catch (Exception e) {
            System.out.println(e.toString() + " loginChecker()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return email;

    }

    public List<Books> getAllBooks() {

        try {
            List<Books> books = new ArrayList<>();

            String bookQuery = "SELECT id, title, author, isbn, publisher, purchase_price, category FROM books";

            con = MyConnection.getConnection();
            ps = con.prepareStatement(bookQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                books.add(book);

            }
            return books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public List<E_Books> getAllEBooks() {

        try {
            List<E_Books> e_books = new ArrayList<>();

            String bookQuery = "SELECT * FROM e_books";

            con = MyConnection.getConnection();
            ps = con.prepareStatement(bookQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books book = new E_Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setDesc(rs.getString(9));
                book.setDate(rs.getDate(10));
                e_books.add(book);

            }

            return e_books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public void deleteGuest(Guest guest) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + guest.getEmail() + "'";

        String deleteLibraryCard = "DELETE FROM librarycards WHERE guests_id=?";

        String deleteGuest = "DELETE FROM guests WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();

            ps = con.prepareStatement(deleteLibraryCard);
            ps.setInt(1, guest.getId());
            ps.executeUpdate();

            ps = con.prepareStatement(deleteGuest);
            ps.setInt(1, guest.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteAdmin(Admin admin) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + admin.getEmail() + "'";

        String deleteAdmin = "DELETE FROM admins WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();

            ps = con.prepareStatement(deleteAdmin);
            ps.setInt(1, admin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteLibrarian(Librarian librarian) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + librarian.getEmail() + "'";

        String deleteLibrarian = "DELETE FROM librarians WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();

            ps = con.prepareStatement(deleteLibrarian);
            ps.setInt(1, librarian.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void addBook(Books b) {

        con = MyConnection.getConnection();

        try {
            System.out.println("Creating statement");
            Statement stmt = con.createStatement();
            System.out.println("Executing query");
            stmt.execute("INSERT INTO books(title, author, isbn, publisher, purchase_price, category, placement, description)"
                    + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                    + b.getPublisher() + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "', '" + b.getPlacement() + "', '" + b.getDesc() + "')");

            stmt.close();

            JOptionPane.showMessageDialog(null, "Boken har sparats!");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding a book: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteBook(Books b, String notes) {

        con = MyConnection.getConnection();

        String bookType = "Book";

        Statement stmt;

        try {

            stmt = con.createStatement();

            stmt.execute("DELETE FROM books WHERE id=" + b.getId());

            String deleteBookQuery1 = "INSERT INTO deleted_books (title, author,  publisher, isbn, bookType, purchase_price, category, placement, notes) "
                    + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getPublisher() + "', '" + b.getIsbn() + "', '"
                    + bookType + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "', '" + b.getPlacement() + "', '" + notes + "')";

            stmt.execute(deleteBookQuery1);
            stmt.execute("DELETE FROM books WHERE id=" + b.getId());

            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Den bok är utlånat.");
            //System.out.println("Något gick fel när du har försökt att radera den bok: " + e.getMessage());
            //e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void addEBook(E_Books b) {

        MyConnection tryConnect = new MyConnection();
        con = MyConnection.getConnection();
        System.out.println("INSERT INTO e_books(title, author, isbn, publisher, purchase_price, category, descript) "
                + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                + b.getPublisher() + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "', '" + b.getDesc() + "')");
        try {
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO e_books(title, author, isbn, publisher, purchase_price, category, descript) "
                    + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                    + b.getPublisher() + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "', '" + b.getDesc() + "')");

            stmt.close();

            JOptionPane.showMessageDialog(null, "E-Boken har sparats!");

        } catch (Exception e) {
            System.out.println("Soemthing went wrong while adding an e-book: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteE_Book(E_Books b) {

        con = MyConnection.getConnection();

        String deleteE_BookQuery = "DELETE FROM e-books WHERE id=?";

        try {
            ps = con.prepareStatement(deleteE_BookQuery);
            ps.setInt(1, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Något gick fel när du har försökt att radera denna e-bok: " + e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<LibraryCards> blockedCards() {

        String blockedListQuery = "select concat( first_name,\" \", last_name)as fullname, "
                + "guests.id, category from guests join \n"
                + "librarycards on guests.id = librarycards.guests_id\n"
                + "where entry = 1;";

        ArrayList<LibraryCards> blockedCards = new ArrayList<LibraryCards>();
        LibraryCards currentList;

        con = MyConnection.getConnection();
        PreparedStatement check;

        try {

            check = con.prepareStatement(blockedListQuery);
            ResultSet rs = check.executeQuery();
            System.out.println();
            while (rs.next()) {
                currentList = new LibraryCards(rs.getString("fullname"),
                        rs.getInt("guests.id"),
                        rs.getString("category"));

                blockedCards.add(currentList);
            }

            check.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString() + " blockedCards()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return blockedCards;

    }

    // All elements of librarycards table in database are included 
    public ArrayList<LibraryCards> getBlockedCards() {

        String blockedListQuery = "select * from librarycards where entry = 1;";

        ArrayList<LibraryCards> blockedCards = new ArrayList<LibraryCards>();
        LibraryCards currentList;

        con = MyConnection.getConnection();
        PreparedStatement check;

        try {

            check = con.prepareStatement(blockedListQuery);
            ResultSet rs = check.executeQuery();
            System.out.println();
            while (rs.next()) {
                currentList = new LibraryCards(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));

                blockedCards.add(currentList);
            }

            check.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString() + " getBlockedCards()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return blockedCards;

    }

    public ArrayList<LibraryCards> getAllCards() {
        String query = "select guests_id, concat(first_name, ' ', last_name)as fullname,\n"
                + "entry, category from librarycards join guests on guests_id = guests.id;";

        ArrayList<LibraryCards> allCardsList = new ArrayList<LibraryCards>();
        LibraryCards list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new LibraryCards(rs.getInt("guests_id"),
                        rs.getString("fullname"),
                        rs.getInt("entry"),
                        rs.getString("category"));
                allCardsList.add(list);
            }

            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString() + " getAllCards()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allCardsList;
    }

    public ArrayList<LibraryCards> getGuestsLibraryCardsByGuestList(ArrayList<Guest> guests) {

        ArrayList<LibraryCards> cards = new ArrayList<>();
        con = MyConnection.getConnection();
        for (Guest g : guests) {
            String query = "select guests_id, concat(first_name, ' ', last_name)as fullname,\n"
                    + "entry, category from librarycards inner join guests on guests_id = guests.id WHERE guests.id = " + g.getId();

            try {
                Statement statement = con.createStatement();
                rs = statement.executeQuery(query);

                while (rs.next()) {
                    LibraryCards libraryCard = new LibraryCards();
                    libraryCard.setGuestId(rs.getInt(1));
                    libraryCard.setFullname(rs.getString(2));
                    libraryCard.setEntry(rs.getInt(3));
                    libraryCard.setCategory(rs.getString(4));
                    cards.add(libraryCard);
                }
                
                statement.close();
            } catch (SQLException e) {

            } finally {
                try {
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return cards;
    }

    // entry is a boolean type in database,where 0 = false which means unblocked card 
    // and 1 = true which is blocked card
    public void updateLibraryCards(int entry, int userId, String category) {
        String query = "";
        if (entry == 1) {
            query = "UPDATE librarycards SET entry = 1, category = '" + category + "' WHERE id = " + userId + ";";
        } else if (entry == 0) {
            query = "UPDATE librarycards SET entry = 0, category = '' WHERE id = " + userId + ";";
        }

        con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(query);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.toString() + " updateLibraryCards()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByTitle(String title) {
        ArrayList<Books> foundBooks = new ArrayList<>();

        String query = "SELECT * FROM books WHERE title LIKE '%" + title + "%'";

        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundBooks;
    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByCategory(String category) {
        ArrayList<Books> foundBooks = new ArrayList<>();
        String query = "SELECT * FROM books WHERE category = '" + category + "'";

        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return foundBooks;
    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByAuthor(String author) {
        ArrayList<Books> foundBooks = new ArrayList<>();

        con = MyConnection.getConnection();
        String query = "SELECT * FROM books WHERE author LIKE '%" + author + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundBooks;
    }

    // Returning NULL if no book found, Catch nullpointer when trying to find book!
    public Books findBookByIsbn(String isbn) {
        String query = "SELECT * FROM books WHERE isbn = '" + isbn + "'";

        con = MyConnection.getConnection();
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                return book;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    // Returning NULL if no book found, Catch nullpointer when trying to find book!
    public ArrayList<Books> findBooksByIsbn(String isbn) {
        String query = "SELECT * FROM books WHERE isbn = '" + isbn + "'";
        ArrayList<Books> books = new ArrayList<Books>();
        con = MyConnection.getConnection();
        try {
//            ps = con.prepareStatement(query);
//            rs = ps.executeQuery();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                book.setDesc(rs.getString(9));
                books.add(book);

            }

            statement.close();
            
            return books;
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    // Returning NULL if no book found, Catch nullpointer when trying to find book!
    public E_Books findEBookByIsbn(String isbn) {
        String query = "SELECT * FROM e_books WHERE isbn = '" + isbn + "'";

        con = MyConnection.getConnection();
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                E_Books ebook = new E_Books();
                ebook.setId(rs.getInt(1));
                ebook.setTitle(rs.getString(2));
                ebook.setAuthor(rs.getString(3));
                ebook.setIsbn(rs.getString(4));
                ebook.setPublisher(rs.getString(5));
                ebook.setPurchase_price(rs.getDouble(6));
                ebook.setCategory(rs.getString(7));
                ebook.setDesc(rs.getString(9));
                return ebook;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public ArrayList<E_Books> findEBooksByTitle(String title) {

        ArrayList<E_Books> foundEBooks = new ArrayList<>();
        con = MyConnection.getConnection();
        String query = "SELECT * FROM e_books WHERE title LIKE '%" + title + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<E_Books> findEBooksByAuthor(String author) {

        ArrayList<E_Books> foundEBooks = new ArrayList<>();
        con = MyConnection.getConnection();
        String query = "SELECT * FROM e_books WHERE author LIKE '%" + author + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<E_Books> findEBooksByCategory(String category) {
        ArrayList<E_Books> foundEBooks = new ArrayList<>();

        con = MyConnection.getConnection();

        String query = "SELECT * FROM e_books WHERE category = '" + category + "'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<DeletedBook> getRemovedBooks() {

        con = MyConnection.getConnection();
        //  ArrayList<DeletedBook> RemovedBooks = new ArrayList<>();

        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM deleted_books");
            while (rs.next()) {
                DeletedBook deletedBook = new DeletedBook();
                deletedBook.setId(rs.getInt(1));
                deletedBook.setTitle(rs.getString("title"));
                deletedBook.setAuthor(rs.getString("author"));
                deletedBook.setPublisher(rs.getString("publisher"));
                deletedBook.setIsbn(rs.getString("isbn"));
                deletedBook.setBookType(rs.getString("bookType"));
                deletedBook.setPurchasePrice(rs.getDouble("purchase_price"));
                deletedBook.setCategory(rs.getString("category"));
                deletedBook.setNotes(rs.getString("notes"));

            }
            
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void borrowBooks(int bookId, int libraryCardId) {

        String query = "insert into borrowed_books(book_id, librarycard_id,"
                + " return_date) values(? , ? , date_add(curdate(), interval 31 day));";
        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            ps.setInt(2, libraryCardId);
            ps.execute();
        } catch (SQLException e) {

        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void borrowEBooks(int eBookId, int libraryCardId) {

        String query = "insert into borrowed_ebooks(ebook_id, librarycard_id,"
                + " return_date) values(? , ? , date_add(curdate(), interval 31 day));";
        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, eBookId);
            ps.setInt(2, libraryCardId);
            ps.execute();
        } catch (SQLException e) {

        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // Returning NULL if no book found, Catch nullpointer when trying to find book!
    public LibraryCards findLibrarycardByEmail(String guestEmail) {
        String query = "select librarycards.id, librarycards.guests_id, librarycards.notes, "
                + "librarycards.category, librarycards.entry from librarycards join "
                + "guests on guests.id = librarycards.guests_id "
                + "where guests.email = '" + guestEmail + "' ;";

        con = MyConnection.getConnection();
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                LibraryCards libraryCard = new LibraryCards();
                libraryCard.setId(rs.getInt(1));
                libraryCard.setGuestId(rs.getInt(2));
                libraryCard.setNotes(rs.getString(3));
                libraryCard.setCategory(rs.getString(4));
                libraryCard.setEntry(rs.getInt(5));

                return libraryCard;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage() + " finLibraryCardByEmail()");

        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public ArrayList<BorrowedBooks> getAllBorrowedBooks() {
        String query = "select * from borrowed_books;";

        ArrayList<BorrowedBooks> borrowedBooks = new ArrayList<BorrowedBooks>();
        BorrowedBooks list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new BorrowedBooks(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getDate(5));
                borrowedBooks.add(list);
            }
            
            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString() + " getAllBorrowedBooks()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return borrowedBooks;
    }

    public ArrayList<BorrowEBooks> getAllBorrowedEBooks() {
        String query = "select * from borrowed_ebooks;";

        ArrayList<BorrowEBooks> borrowedEBooks = new ArrayList<BorrowEBooks>();
        BorrowEBooks borrowedEBook;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                borrowedEBook = new BorrowEBooks(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getDate(5));
                borrowedEBooks.add(borrowedEBook);
            }
            
            ps.close();
            rs.close();
            
            return borrowedEBooks;
        } catch (Exception e) {
            System.out.println(e.toString() + " getAllBorrowedBooks()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return borrowedEBooks;
    }

    public ArrayList<Books> getBorrowedBooksByCardId(int libraryCardId) {
        String query = "select * from books join borrowed_books on book_id = "
                + "books.id where librarycard_id = " + libraryCardId + ";";

        ArrayList<Books> borrowedBooks = new ArrayList<Books>();
        Books list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new Books(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9));
                borrowedBooks.add(list);
            }
            
            ps.close();
            rs.close();
            
            return borrowedBooks;

        } catch (Exception e) {
            System.out.println(e.toString() + " getBorrowedBooksByCardId()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return borrowedBooks;
    }

    public void returnBook(int bookId) {

        //hitta bokens id i borrowed_books med hjälp av library card
        //ta bort boken från borrowed_books
        //hitta boken i books plussa en till in_stock där id är samma som i borrowed_books
        MyConnection myConnection = new MyConnection();

        try {
            con = myConnection.getConnection();
            Statement stmt = con.createStatement();

            stmt.execute("DELETE FROM borrowed_books WHERE book_id = " + bookId + "");
            System.out.println("Book Removed from borrowed_books");
            
            stmt.close();
        } catch (Exception e) {
            System.out.println("Something went wrong while returning a book: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Seminar> findSeminar() {
        ArrayList<Seminar> seminar = new ArrayList<Seminar>();
        try {
            MyConnection tryConnection = new MyConnection();

            Seminar currentSeminar;

            con = tryConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT id, Title, Speaker, Location, StartDate, CountVisitor, Description, Program FROM seminarium ORDER BY StartDate ASC");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentSeminar = new Seminar(results.getInt("id"),
                        results.getString("Title"),
                        results.getString("Speaker"),
                        results.getString("Location"),
                        results.getString("StartDate"),
                        results.getInt("CountVisitor"),
                        results.getString("Description"),
                        results.getString("Program"));
                seminar.add(currentSeminar);
                currentSeminar = null;
            }
            
            results.close();
            stmt.close();

            return seminar;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seminar;
    }

    public Seminar findSeminarByTitle(String title) {
        Seminar s = new Seminar();

        try {

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM seminarium WHERE Title = '" + title + "'");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                s = new Seminar(Integer.parseInt(results.getString("id")),
                        results.getString("Title"),
                        results.getString("Speaker"),
                        results.getString("Location"),
                        results.getString("StartDate"),
                        results.getInt("CountVisitor"),
                        results.getString("Description"),
                        results.getString("Program"));
            }

            results.close();
            stmt.close();

            return s;
        } catch (SQLException e) {
            System.out.println("Something went wrong in findSeminarByTitle(): " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public void addSeminar(Seminar seminar) {

        try {

            MyConnection connect = new MyConnection();
            con = connect.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO seminarium(Title, Speaker, Location, StartDate, CountVisitor, Description, Program) VALUES('"
                    + seminar.getTitle() + "', '"
                    + seminar.getSpeaker() + "', '"
                    + seminar.getLocation() + "', '"
                    + seminar.getStartDate() + "', '"
                    + Integer.toString(seminar.getCountVisitor()) + "', '"
                    + seminar.getSeminariumDescription() + "', '"
                    + seminar.getProgramDescription() + "')");

            stmt.close();

            JOptionPane.showMessageDialog(null, "Seminarium har sparats!");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding a seminar: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void bookSeminar(LibraryCards g, Seminar s) {

        try {
            MyConnection tryConnection = new MyConnection();
            con = tryConnection.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT library_card_id FROM bookings WHERE seminar_id = " + s.getId() + "");
            int guestExists = 0;
            
            // checking if guest is already booked in
            while(rs.next()) {
                guestExists = rs.getInt("library_card_id");
                System.out.println(guestExists + " = " + g.getId());
                if(guestExists == g.getId()) {
                    JOptionPane.showMessageDialog(null, "Du är redan inbokad.");
                    rs.close();
                    stmt.close();
                    con.close();
                    return;
                }
            }
            rs.close();
            
            // receiving max num of guests
            rs = stmt.executeQuery("SELECT CountVisitor FROM seminarium WHERE id = " + s.getId() + "");
            rs.next();
            int nrOfVisitors = rs.getInt("CountVisitor");
            rs.close();

            // checking for available spaces
            if ((nrOfVisitors - 1) >= 0) {
                // inserting guest if there are available spaces
                stmt.execute("INSERT INTO bookings(seminar_id, library_card_id) VALUES(" + s.getId() + ", " + g.getId() + ")"); // insert guest into bokings table
                stmt.execute("UPDATE seminarium SET CountVisitor = " + (nrOfVisitors - 1) + " WHERE id = " + s.getId() + ""); // update seminarium visitors
                JOptionPane.showMessageDialog(null, "Bokningen är genomfört!");
            } else {
                JOptionPane.showMessageDialog(null, "Seminarium är fullbokad.");
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong while booking seminar: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cancelSeminarReservation (Guest g, String title){
       LibraryCards libraryCard = findLibrarycardByEmail(g.getEmail());
       Seminar seminar = findSeminarByTitle(title);
        String query = "DELETE FROM bookings WHERE library_card_id =" + libraryCard.getId() + " AND seminar_id = " + seminar.getId();
        
        con = MyConnection.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally{
           try {
               con.close();
           } catch (SQLException ex) {
               Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        
    }
    public ArrayList<Books> groupAllBooksByIsbn() {
        String query = "Select title, author, isbn, publisher, purchase_price, category,\n"
                + " placement, books.description, count(*) as copies from books group by isbn;";

        ArrayList<Books> borrowedBooks = new ArrayList<Books>();
        Books list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new Books(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
                borrowedBooks.add(list);
            }
            
            rs.close();
            ps.close();
            
            return borrowedBooks;

        } catch (Exception e) {
            System.out.println(e.toString() + " groupAllBooksByIsbn()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return borrowedBooks;
    }

    public Books findBorrowedBookById(int id) {
        con = MyConnection.getConnection();

        String query = "SELECT book_id, title, author  FROM borrowed_books INNER JOIN books ON borrowed_books.book_id = books.id WHERE book_id =" + id;
        Books book = new Books();
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {

                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                return book;

            }
            
            rs.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        book.setId(-1);
        return book;
    }

    
    public ArrayList<String> readBook(int idEbook) throws FileNotFoundException, IOException, SQLException {
        InputStream input = null;
        String test = "";
       ArrayList<String> book = new ArrayList<String>();
        String query = "SELECT book_file FROM e_books_files where id_e_book =?";
        PreparedStatement ps;
      MyConnection tryConnection = new MyConnection();
      Connection conn = tryConnection.getConnection();
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, idEbook);
            rs = ps.executeQuery();


            
            if (rs.next()) {
                input = rs.getBinaryStream("book_file");
                       Scanner sc = new Scanner(input);
                       
            
                
                

                while (sc.hasNext()) {
                   test = sc.nextLine();
                   book.add(test);
                    System.out.println(book.toString());

                    
                }
                return book;
                
            
            }
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
          
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    return book;

    }
    
    public ArrayList<Booking> getAllBookedSeminars() {
        String query = "select * from bookings;";

        ArrayList<Booking> bookedSeminars = new ArrayList<Booking>();
        Booking list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new Booking(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3));
                       bookedSeminars.add(list);
            }
            
            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString() + " getAllBookedSeminars()");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookedSeminars;
    }
    
}
